package ai.sxr.shoppingla.utils.lock;

public interface LockService {
    String lock();
    void failLock();
    String properLock();
}