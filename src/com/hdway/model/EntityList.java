package com.hdway.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.hdway.utils.Logger;
import com.hdway.utils.Utils;


public class EntityList extends ArrayList<BaseEntity> {
	public BaseEntity getEntityById(long id) {
		for( BaseEntity entity : this ) {
			if( entity.id == id ) {
				return entity;
			}
		}
		return null;
	}
	
	public int indexOfEntityById(long id) {
		for( int i = 0; i < this.size(); i++ ) {
			if( this.get(i).id == id ) {
				return i;
			}
		}
		return -1;
	}
	
	public void sortBy(String field, String fieldType, boolean ascending) {
		Collections.sort(this, new CustomComparator(field, fieldType, ascending));
	}
	public class CustomComparator implements Comparator<BaseEntity> {
		public String field;
		public String type;
		public int orderFactor = -1;

		public CustomComparator(String field, String type, boolean ascending) {
			super();
			this.field = field;
			this.type = type;
			orderFactor = ascending ? 1 : -1;
		}

		@Override
		public int compare(BaseEntity lhs, BaseEntity rhs) {
			
			Object leftValue;
			try {
				leftValue = lhs.getClass().getField(field).get(lhs);
				Object rightValue = rhs.getClass().getField(field).get(rhs);
				if( type.equals(BaseEntity.FIELD_INT) ){
					return orderFactor * ((Integer) leftValue - (Integer) rightValue);
				} else if( type.equals(BaseEntity.FIELD_LONG) ){
					return orderFactor * (int)((Long) leftValue - (Long) rightValue);
				} else if( type.equals(BaseEntity.FIELD_TEXT) ) {
					return orderFactor * ((String)leftValue).compareTo((String)rightValue);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Logger.LogE("Exception in comparator: " + e.getMessage());
			} 			
			
			return 0;
		}
		
		
	}
}
