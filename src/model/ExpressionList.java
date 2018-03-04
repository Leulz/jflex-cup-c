package model;

import java.util.ArrayList;

public class ExpressionList implements ExpressionIF {
  ArrayList<Expression> expressionList;
  
  public ExpressionList() {
    this.expressionList = new ArrayList<Expression>();
  }
  
  public ExpressionList(Expression e) {
    this();
    
    this.expressionList.add(e);
  }
  
  public int size() {
    return this.getExpressionList().size();
  }
  
  @Override
  public boolean isExpressionList() {
    return true;
  }
  
  public ArrayList<Expression> getExpressionList() {
    return this.expressionList;
  }

  public void add(Expression e) {
    this.expressionList.add(0, e);
  }
  
  @Override
  public String toString() {
    return expressionList.toString();
  }
}