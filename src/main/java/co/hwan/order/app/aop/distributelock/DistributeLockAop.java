package co.hwan.order.app.aop.distributelock;

import co.hwan.order.app.common.annotations.DistributeLock;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@RequiredArgsConstructor
@Component
public class DistributeLockAop {
    private static final String REDISSON_KEY_PREFIX = "RLOCK_";
    private final RedissonClient redissonClient;

    @Around("@annotation(co.hwan.order.app.common.annotations.DistributeLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributeLock distributeLock = method.getAnnotation(DistributeLock.class);     // (1)

        String key = REDISSON_KEY_PREFIX;
        RLock rLock = redissonClient.getLock(key);    // (3)

        try {
            boolean available = rLock.tryLock(distributeLock.waitTime(), distributeLock.leaseTime(), distributeLock.timeUnit());    // (4)
            if (!available) {
                return false;
            }
            log.info("get lock success {}" , key);
            return joinPoint.proceed();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new InterruptedException();
        } finally {
            rLock.unlock();    // (6)
        }
    }


}
