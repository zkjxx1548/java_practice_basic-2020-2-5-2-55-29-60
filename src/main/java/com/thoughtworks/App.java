package com.thoughtworks;

import java.util.Arrays;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("请点餐（菜品Id x 数量，用逗号隔开）：");
    String selectedItems = scan.nextLine();
    String summary = bestCharge(selectedItems);
    System.out.println(summary);
  }

  /**
   * 接收用户选择的菜品和数量，返回计算后的汇总信息
   *
   * @param selectedItems 选择的菜品信息
   */
  public static String bestCharge(String selectedItems) {
    // 此处补全代码
    int allPrice = 0;
    int fullReduction;
    int halfReduction = 0;
    String halfItemNames = "";
    String[] arrSelect = selectedItems.split(",");
    String res = "============= 订餐明细 =============\n";
    for (int i = 0; i < arrSelect.length; i++) {
      String regex = " x ";
      String[] itemIdAndCount = arrSelect[i].split(regex);//
      String itemId = itemIdAndCount[0];
      int itemCount = Integer.parseInt(itemIdAndCount[1]);
      int itemIndex = Arrays.binarySearch(getItemIds(), itemId);//mod
      int itemPrice = (int) getItemPrices()[itemIndex];
      allPrice += itemCount*itemPrice;
      if (isHalfItem(itemId, getHalfPriceIds())) {
        halfReduction += (itemCount * itemPrice * 0.5);
        halfItemNames = getHalfItemNames(halfItemNames, itemIndex, getItemNames());
      }
      res += getItemNames()[itemIndex] + regex + itemCount + " = " + itemCount * itemPrice + "元\n";
    }
    res += "-----------------------------------\n";
    fullReduction = getFullReduction(allPrice);

    //判断用什么优惠
    String fullStr = "满30减6元，省%d元\n";
    String halfStr = "指定菜品半价(%s)，省%d元\n";
    if (fullReduction != 0 || halfReduction !=0) {
      if (fullReduction >= halfReduction) {//
        res += getFullReductionStr(fullReduction, fullStr) + getAllPriceStr(allPrice - fullReduction);
      } else {
        res += getHalfReductionStr(halfReduction, halfItemNames, halfStr) + getAllPriceStr(allPrice - halfReduction);
      }
    } else {
      res += getAllPriceStr(allPrice);
    }
    return res;
  }

  //判断是否为半价商品
  public static boolean isHalfItem(String itemId, String[] halfItemIds) {
    for (String s : halfItemIds) {
      if (s.equals(itemId)) {
        return true;
      }
    }
    return false;
  }

  //获取已选半价商品名称的集合
  public static String getHalfItemNames(String halfItemNames, int itemIndex, String[] itemNames) {
    if (halfItemNames.equals("")) {
      halfItemNames += itemNames[itemIndex];
    } else {
      halfItemNames += "，" + itemNames[itemIndex];
    }
    return halfItemNames;
  }

  //获取满减优惠
  public static int getFullReduction(int allPrice) {
    int fullReductionBasic = 30;
    int fullReductionPreferential = 6;
    return allPrice >= fullReductionBasic ? fullReductionPreferential : 0;
  }

  //获取满减优惠信息的字符串
  public static String getFullReductionStr(int reduction, String str) {
    String str1 = "使用优惠:\n";
    String str2 = "-----------------------------------\n";
    return str1 + String.format(str, reduction) + str2;
  }

  //获取半价优惠信息的字符串
  public static String getHalfReductionStr(int reduction, String halfItemNames, String str) {
    String str1 = "使用优惠:\n";
    String str2 = "-----------------------------------\n";
    return str1 + String.format(str, halfItemNames, reduction) + str2;
  }

  //获取总价字符串
  public static String getAllPriceStr(int price) {
    String str1 = "===================================";
    return String.format("总计：%d元\n", price) + str1;
  }

  /**
   * 获取每个菜品依次的编号
   */
  public static String[] getItemIds() {
    return new String[]{"ITEM0001", "ITEM0013", "ITEM0022", "ITEM0030"};
  }

  /**
   * 获取每个菜品依次的名称
   */
  public static String[] getItemNames() {
    return new String[]{"黄焖鸡", "肉夹馍", "凉皮", "冰粉"};
  }

  /**
   * 获取每个菜品依次的价格
   */
  public static double[] getItemPrices() {
    return new double[]{18.00, 6.00, 8.00, 2.00};
  }

  /**
   * 获取半价菜品的编号
   */
  public static String[] getHalfPriceIds() {
    return new String[]{"ITEM0001", "ITEM0022"};
  }
}
