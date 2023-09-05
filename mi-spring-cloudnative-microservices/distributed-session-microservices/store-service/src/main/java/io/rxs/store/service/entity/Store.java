package io.rxs.store.service.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="STORE")
public class Store implements Serializable {

	private static final long serialVersionUID = 3478349780068265233L;

	@Id
    @Column(name="STORE_ID")
    private Long storeId;

    @Column(name="STORE_NAME")
    private String storeName;

    public Store(Long storeId, String storeName) {
        this.storeId = storeId;
        this.storeName = storeName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Store() {
    }

    @Override
    public String toString() {
        return "Store{" +
                "storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                '}';
    }
}
