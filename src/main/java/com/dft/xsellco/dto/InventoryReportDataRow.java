package com.dft.xsellco.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;


@Data
public class InventoryReportDataRow {

    @CsvBindByName(column = "sku")
    String sku;

    @CsvBindByName(column = "marketplace")
    String marketplace;

    @CsvBindByName(column = "merchant_id")
    String merchantId;

    @CsvBindByName(column = "price_min")
    String priceMin;

    @CsvBindByName(column = "price_max")
    String priceMax;

    @CsvBindByName(column = "unit_cost")
    String unitCost;

    @CsvBindByName(column = "unit_currency")
    String unitCurrency;

    @CsvBindByName(column = "shipping_cost")
    String shippingCost;

    @CsvBindByName(column = "shipping_currency")
    String shippingCurrency;

    @CsvBindByName(column = "pickpack_cost")
    String pickpackCost;

    @CsvBindByName(column = "pickpack_currency")
    String pickpackCurrency;

    @CsvBindByName(column = "rrp_price")
    String rrpPrice;

    @CsvBindByName(column = "rrp_currency")
    String rrpCurrency;

    @CsvBindByName(column = "vat_rate")
    String vatRate;

    @CsvBindByName(column = "fee_listing")
    String feeListing;

    @CsvBindByName(column = "fba_fee")
    String fbaFee;

    @CsvBindByName(column = "fba_currency")
    String fbaCurrency;

    @CsvBindByName(column = "repricer_name")
    String repricerName;

    @CsvBindByName(column = "sales_rule")
    String velocityRepricerName;

    @CsvBindByName(column = "estimated_seller_count")
    String estimatedSellerCount;

    @CsvBindByName(column = "lowest_landed_price")
    String lowestLandedPrice;

    @CsvBindByName(column = "buy_box_price")
    String buyBoxPrice;

    @CsvBindByName(column = "buy_box_winner")
    String buyBoxWinner;

    @CsvBindByName(column = "my_price")
    String myPrice;

    @CsvBindByName(column = "my_shipping")
    String myShipping;

    @CsvBindByName(column = "fba")
    String fba;

    @CsvBindByName(column = "asin")
    String asin;

    @CsvBindByName(column = "open_date")
    String openDate;

    @CsvBindByName(column = "regular_price")
    String regularPrice;

    @CsvBindByName(column = "sale_start_date")
    String saleStartDate;

    @CsvBindByName(column = "sale_end_date")
    String saleEndDate;

    @CsvBindByName(column = "last_sale")
    String lastSale;

    @CsvBindByName(column = "quantity")
    String quantity;

    @CsvBindByName(column = "last_sales_rank")
    String lastSalesRank;
}