package com.example.hogwartsPoints.dto.id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ItemEntityId implements Serializable {
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "order_id")
    private String orderId;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntityId that = (ItemEntityId) o;
        return Objects.equals(id, that.id) && Objects.equals(orderId, that.orderId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, orderId);
    }
}
