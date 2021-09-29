package vn.alpaca.ecommerce.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Carts")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String note;

	@Temporal(TemporalType.DATE)
	private Date purchaseDate = new Date();

	@OneToOne
	@JoinColumn(name = "customer_id")
	private User customer;

	@OneToMany(mappedBy = "cart")
	private List<CartDetail> cartDetails;

//	@OneToOne
//	@JoinColumn(name = "delivery_info_id")
//	private DeliveryInfo deliveryInfo;

}
