package com.castis.adgateway.dto.csis;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

//<Category>
//<CategoryId>68</CategoryId>
//<CategoryName>꽃보다 남자</CategoryName>
//</Category>

public class Category implements Serializable {
	
	static final long	serialVersionUID = 3877809985554150327L;
	
	String 			categoryId;		//int->String 으로 변경(2012.2월2주기 개발내용)
	String			categoryName;
	
	
	//----------------------------------------------------------
	// Getter/setter methods


	@XmlElement(name="CategoryId")
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@XmlElement(name="CategoryName")
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
	
	// - - - - - - - - - - - - - - - -
	// Public methods
	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", categoryName="	+ categoryName + "]";
	}

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Category(String categoryId, String categoryName) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	

}
