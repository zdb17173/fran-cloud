package org.fran.cloud.aws.lambda;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.util.HashMap;
import java.util.Map;

public class GeneralDynamoDBOperate {

    static AmazonDynamoDB _client = null;
    static String _table_name = "dynamodb_test";
    static {
        _client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.CN_NORTH_1)
            .build();
    }

    //根据Id，查询item
    public static Result get(Data data, Context context){
        try{
            Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
            key.put("id", new AttributeValue().withS(data.getId()));
            GetItemRequest request = new GetItemRequest()
                    .withKey(key)
                    .withTableName(_table_name);


            GetItemResult item = _client.getItem(request);
            Map<String, AttributeValue> r = item.getItem();

            Map<String, String> value = new HashMap<String, String>();
            for(String k : r.keySet()){
                AttributeValue attr = r.get(k);
                if(attr.getS() != null)
                    value.put(k, attr.getS());
                else if(attr.getN() != null)
                    value.put(k, attr.getN());
                else if(attr.getBOOL() != null)
                    value.put(k, attr.getBOOL().toString());
                else
                    value.put(k, null);
            }

            Result res = new Result();
            res.setStatus(200);
            res.setRes(value);
            return res;
        }catch (Exception e){
            context.getLogger().log(e.getMessage());
            Result res = new Result();
            res.setStatus(500);
            res.setDescription(e.getMessage());
            return res;
        }
    }

    //用于统计计数
    public static Result add(Add add, Context context){
        Result res = new Result();
        if(add == null || add.getId() == null || add.getItem() == null
                || add.getItem().size() == 0){
            res.setStatus(500);
            res.setDescription("param error["+ add +"]");
            return res;
        }else{
            try{
                Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
                key.put("id", new AttributeValue().withS(add.getId()));


                HashMap<String, AttributeValue> attrValues =
                        new HashMap<String,AttributeValue>();
//                attrValues.put(":incr", new AttributeValue().withN("1"));

                Map<String, Integer> item = add.getItem();
                String updateExpression = "SET ";// SET price1 = price1 + :incr, price2 = price2 + :incr
                int i = 0;
                for(String k : item.keySet()){
                    Integer incr = item.get(k);
                    if(incr == null || incr == 0)
                        continue;
                    else
                        attrValues.put(":"+ k, new AttributeValue().withN(incr.toString()));

                    if(i!= 0)
                        updateExpression += " , ";

                    updateExpression += k + " = " + k + "+ :"+ k;

                    i ++;
                }

                LambdaLogger logger = context.getLogger();
                logger.log("updateExpression : " + updateExpression);
                logger.log("attrValues : " + attrValues);

                UpdateItemRequest updateItemRequest = new UpdateItemRequest()
                        .withKey(key)
                        .withExpressionAttributeValues(attrValues)
                        .withUpdateExpression(updateExpression)//"SET price1 = price1 + :incr, price2 = price2 + :incr"
                        .withTableName(_table_name);

                UpdateItemResult r = _client.updateItem(updateItemRequest);
//                System.out.println(r);

                res.setStatus(200);
                return res;
            }catch (Exception e){
                context.getLogger().log(e.getMessage());
                res.setStatus(500);
                res.setDescription(e.getMessage());
                return res;
            }
        }
    }

    //推送数据
    public static Result put(Data data, Context context){
        Result res = new Result();
        try{

            HashMap<String, AttributeValue> key_to_put =
                    new HashMap<String,AttributeValue>();
            key_to_put.put("id", new AttributeValue(data.getId()));

            for(String column : data.getValue().keySet()){
                String value = data.getValue().get(column);
                key_to_put.put(column , new AttributeValue(value == null? "" : value));
            }

            PutItemRequest put = new PutItemRequest()
                    .withItem(key_to_put)
                    .withTableName(_table_name);

            PutItemResult putRes = _client.putItem(put);

            res.setStatus(200);
            return res;
        }catch (Exception e){
            context.getLogger().log(e.getMessage());
            res.setDescription(e.getMessage());
            res.setStatus(500);
            return res;
        }

    }
}