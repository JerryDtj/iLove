# springCloud学习之消费者(基础)

## 一、代码

```
 @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String dc(){
        List<String> s=  discoveryClient.getServices();
        s.forEach(System.out::println);
        //调用ServiceInstanceChooser.choose(String serviceId)
        ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-client");
        String url = "http://"+ serviceInstance.getHost()
        	+":"+serviceInstance.getPort()+"/dc";
        String result = restTemplate.getForObject(url,String.class);
        return result;
    }
```

# 二、代码解读

1. uml图如下: 实线为继承,虚线为实现

   ![](/Users/dengtianjiao/Desktop/LoadBalancerClient.png)

2. loadBalancerClient三个方法解读:

   execute()方法，均是用来执行请求的，reconstructURI()是用来重构URL的

3. RibbonLoadBalancerClient的choose方法调用如下图:

![屏幕快照 2019-06-16 下午7.43.12](/Users/dengtianjiao/Desktop/屏幕快照 2019-06-16 下午7.43.12.png)

