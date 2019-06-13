package com.love.ilove;

import com.love.ilove.domain.Role;
import com.love.ilove.enums.RoleStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Jerry
 * @Date 2019-06-09 08:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

//    @Resource(name="redisTemplate")
//    private ValueOperations<String, Object> vOps;
//
//    @Resource(name="redisTemplate")
//    private HashOperations<String,String,Object> hashOperations;

    @Test
    public void StringTest(){
        ValueOperations vOps = redisTemplate.opsForValue();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        UserTest u = new UserTest();
        u.setId(1);
        u.setPassword("df11");
        u.setUsername("姓名");
        u.setAccountNonLocked(true);
        int i=0;
        List<Role> roleList = new ArrayList<>();
        while (i<3){
            i++;
            Role role = new Role();
            role.setName("权限"+i);
            role.setRoleStatus(RoleStatus.NORMALITY);
            role.setId(i);
            roleList.add(role);
        }

        u.setRoleList(roleList);

//        vOps.set("testString","1",10, TimeUnit.MINUTES);
        vOps.set("testString",u,1, TimeUnit.MINUTES);
        UserTest user = (UserTest) vOps.get("testString");
        Assert.assertEquals(3,user.getRoleList().size());
        Assert.assertEquals(Integer.valueOf(1),user.getId());
        Assert.assertEquals(0,user.getRoleList().get(0).getRoleStatus().getKey());
//        Assert.assertEquals("1",vOps.get("testString"));
    }

    @Test
    public void hashTest(){


//        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
//        Long increment = entityIdCounter.getAndIncrement();


        HashOperations hashOperations = redisTemplate.opsForHash();
        String key = "testhash";
        //判断散列中是否存在某个key
        boolean exist = redisTemplate.hasKey(key);

        if (exist){
            redisTemplate.delete(key);
        }
        //如果想用redis自增，必须使用GenericToStringSerializer、StringRedisSerializer序列化，
        // 默认的JdkSerializationRedisSerializer使用的jdk对象序列化，序列化后的值有类信息、版本号等，所以是一个包含很多字母的字符串
//        redisTemplate.setValueSerializer(new GenericToStringSerializer(Object.class));
        redisTemplate.setHashValueSerializer(new GenericToStringSerializer(Object.class));
        //判断散列中是否存在某个key,因为已经清空了，所以这里时没有的
        Assert.assertFalse(hashOperations.hasKey(key,"age"));
        redisTemplate.expire(key,30,TimeUnit.SECONDS);
//        为散列添加或者覆盖一个 key-value键值对
        hashOperations.put(key,"name","redis");
        hashOperations.put(key,"age",1);
        Assert.assertEquals("制定key的大小不为2",hashOperations.size(key),Long.valueOf(2));
        Map map = new HashMap();
        map.put("name","aa");
        map.put("age",2);
        map.put("sex",1);
//        为散列添加多个key-value键值对
        hashOperations.putAll(key,map);
        Assert.assertEquals("aa",hashOperations.get(key,"name"));
        Assert.assertEquals(Long.valueOf(3),hashOperations.size(key));
//        为散了中某个值加上 整型 delta
        hashOperations.increment(key,"age",1);
        Assert.assertEquals(3,Integer.parseInt(hashOperations.get(key,"age").toString()));
//        为散列添加一个key-value键值对。如果存在则不添加不覆盖。返回false
        hashOperations.putIfAbsent(key,"name","name");

    }

}

class UserTest{
    private Integer id;

    private String username;

    private String password;

    private List<Role> roleList;

    private boolean accountNonLocked = true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
}
