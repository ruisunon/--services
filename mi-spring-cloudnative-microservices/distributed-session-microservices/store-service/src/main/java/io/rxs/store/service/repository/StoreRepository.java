package io.rxs.store.service.repository;


import io.rxs.store.service.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
}
