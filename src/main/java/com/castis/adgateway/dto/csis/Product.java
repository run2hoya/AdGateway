package com.castis.adgateway.dto.csis;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

//<Product>
//<ProductType>20</ProductType>
//<Price>1000</Price>
//</Product>

public class Product implements Serializable {
	
	static final long	serialVersionUID = 8714976448662537382L;
	
	int			productType;
	double		price;

	
	//----------------------------------------------------------
	// Getter/setter methods


	@XmlElement(name="ProductType")
	public int getProductType() {
		return productType;
	}
	public void setProductType(int productType) {
		this.productType = productType;
	}
	
	@XmlElement(name="Price")
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	
	// - - - - - - - - - - - - - - - -
	// Public methods

	@Override
	public String toString() {
		return "Product [productType=" + productType + ", price=" + price + "]";
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Product(int productType, double price) {
		super();
		this.productType = productType;
		this.price = price;
	}
	
	

}
