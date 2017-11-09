# DefaultApi

All URIs are relative to *http://localhost:8080/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createFruit**](DefaultApi.md#createFruit) | **POST** /fruits | 
[**getFruits**](DefaultApi.md#getFruits) | **GET** /fruits | 


<a name="createFruit"></a>
# **createFruit**
> Object createFruit(fruit)



create a fruit

### Example
```java
// Import classes:
//import io.avalia.fruits.ApiException;
//import io.avalia.fruits.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
Fruit fruit = new Fruit(); // Fruit | 
try {
    Object result = apiInstance.createFruit(fruit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#createFruit");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **fruit** | [**Fruit**](Fruit.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="getFruits"></a>
# **getFruits**
> List&lt;Fruit&gt; getFruits()



get the list of all fruits

### Example
```java
// Import classes:
//import io.avalia.fruits.ApiException;
//import io.avalia.fruits.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
try {
    List<Fruit> result = apiInstance.getFruits();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#getFruits");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Fruit&gt;**](Fruit.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

