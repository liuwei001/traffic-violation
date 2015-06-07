package com.traffic.weizhang.entity;

import java.util.ArrayList;
import java.util.List;

public class Suppliers {

	private List<Supplier> supplierList = new ArrayList<Supplier>();
	
	public void addSupplier(Supplier supplier) {
		supplierList.add(supplier);
	}

	public List<Supplier> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(List<Supplier> supplierList) {
		this.supplierList = supplierList;
	}


}
