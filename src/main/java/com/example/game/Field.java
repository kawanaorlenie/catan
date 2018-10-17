package com.example.game;

public class Field {

	FieldType fieldType;
	int x;
	int y;

	public Field(FieldType fieldType, int x, int y) {
		this.x = x;
		this.y = y;
		this.fieldType = fieldType;
	}
	
	public Field(int x, int y) {
		this.x = x;
		this.y = y;
		this.fieldType = FieldType.BRICK;
	}
	
	public Field(){
		
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public FieldType getFieldType() {
		return fieldType;
	}

}
