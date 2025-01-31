package com.app.core;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;
import redis.clients.jedis.Jedis;

import com.app.entities.Advertisements;
import com.app.entities.CreditCard;
import com.app.entities.Post;
import com.app.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataBaseService {

    private static String REDIS_URL = "redis://default:qA9FYx8ZVtjWC7qX7B7OL6MyYftrJYBE@redis-14467.c228.us-central1-1.gce.cloud.redislabs.com:14467";
    private static String MONGODB_URL = "mongodb+srv://shrshanb:amR6zCrYiD7R3dEl@secoding6.lw2f5p1.mongodb.net/?retryWrites=true&w=majority";

    public static DataBaseService dataBaseService = new DataBaseService();

    private DataBaseService() {
    }

    public static DataBaseService getInstance() {
        return dataBaseService;
    }

    public boolean saveCC(CreditCard creditCard) {
        Jedis jedis = new Jedis(REDIS_URL);
        String key = "creditcard:" + creditCard.getId();

        jedis.hset(key, "userid", String.valueOf(creditCard.getUserId()));
        jedis.hset(key, "number", String.valueOf(creditCard.getCard()));
        jedis.hset(key, "cvv", String.valueOf(creditCard.getCvv()));
        jedis.hset(key, "month", String.valueOf(creditCard.getMonth()));
        jedis.hset(key, "year", String.valueOf(creditCard.getYear()));

        jedis.close();
        return true;
    }

    public boolean saveUser(User user) {
        Jedis jedis = new Jedis(REDIS_URL);
        String usersKey = "users:" + user.getId();
        jedis.hset(usersKey, "username", user.getUserName());
        jedis.hset(usersKey, "password", user.getPassword());
        jedis.hset(usersKey, "fullname", user.getFullName());
        jedis.hset(usersKey, "address", user.getAddress());
        jedis.hset(usersKey, "email", user.getEmailId());
        jedis.hset(usersKey, "type", user.getUserType());
        jedis.hset(usersKey, "dateofbirth", user.getDateOfBirth());

        String emailKey = "email:" + user.getEmailId();
        jedis.hset(emailKey, "userid", String.valueOf(user.getId()));
        jedis.close();
        return true;
    }

    public Optional<User> loadUserByEmailId(String emailId) {
        Jedis jedis = new Jedis(REDIS_URL);
        String emailKey = "email:" + emailId;
        String userId = jedis.hget(emailKey, "userid");
        if (userId != null) {
            String key = "users:" + userId;
            User user = new User();
            user.setId(Integer.parseInt(userId));
            user.setUserName(jedis.hget(key, "username"));
            user.setPassword(jedis.hget(key, "password"));
            user.setFullName(jedis.hget(key, "fullname"));
            user.setAddress(jedis.hget(key, "address"));
            user.setEmailId(jedis.hget(key, "email"));
            user.setUserType(jedis.hget(key, "type"));
            user.setDateOfBirth(jedis.hget(key, "dateofbirth"));

            return Optional.of(user);
        }

        return Optional.empty();
    }

    public boolean updateUserType(int userId, String type) {
        Jedis jedis = new Jedis(REDIS_URL);
        String usersKey = "users:" + userId;
        jedis.hset(usersKey, "type", type);
        jedis.close();
        return true;
    }

    public boolean updateUser(int userId, String name, String full_name, String dob) {
        Jedis jedis = new Jedis(REDIS_URL);
        String usersKey = "users:" + userId;
        jedis.hset(usersKey, "username", name);
        jedis.hset(usersKey, "fullname", full_name);
        jedis.hset(usersKey, "dateofbirth", dob);
        jedis.close();
        return true;
    }

    public List<Post> loadPost(int userId) {
        MongoClient mongoClient = MongoClients.create(MONGODB_URL);
        MongoDatabase database = mongoClient.getDatabase("Project");
        MongoCollection<Document> collection = database.getCollection("post");

        // Create a filter for the given userId
        Bson filter = Filters.eq("userid", userId);
        // Use the filter to find documents with the specified userId
        FindIterable<Document> result = collection.find(filter);

        List<Post> posts = new ArrayList<>();

        for (Document postDocument : result) {
            Post post = new Post();
            post.setId(postDocument.getInteger("id"));
            post.setUserId(postDocument.getInteger("userid"));
            post.setContent(postDocument.getString("content"));
            post.setTitle(postDocument.getString("title"));
            post.setBlocked(postDocument.getString("blocked").equals("true"));
            post.setDateCreated(postDocument.getString("datecreated"));
            post.setLikes(postDocument.getInteger("likes"));
            post.setDisLikes(postDocument.getInteger("dislikes"));

            posts.add(post);
        }
        mongoClient.close();

        return posts;
    }

    public List<Post> loadAllPosts() {
        MongoClient mongoClient = MongoClients.create(MONGODB_URL);
        MongoDatabase database = mongoClient.getDatabase("Project");
        MongoCollection<Document> collection = database.getCollection("post");

        FindIterable<Document> result = collection.find();
        List<Post> posts = new ArrayList<>();

        for (Document postDocument : result) {
            Post post = new Post();
            post.setId(postDocument.getInteger("id"));
            post.setUserId(postDocument.getInteger("userid"));
            post.setContent(postDocument.getString("content"));
            post.setTitle(postDocument.getString("title"));
            post.setBlocked(postDocument.getString("blocked").equals("true"));
            post.setDateCreated(postDocument.getString("datecreated"));
            post.setLikes(postDocument.getInteger("likes"));
            post.setDisLikes(postDocument.getInteger("dislikes"));

            posts.add(post);
        }
        mongoClient.close();

        return posts;
    }

    public boolean savePost(Post post) {
        MongoClient mongoClient = MongoClients.create(MONGODB_URL);
        MongoDatabase database = mongoClient.getDatabase("Project");
        MongoCollection<Document> collection = database.getCollection("post");

        Document postDocument = new Document();
        postDocument.append("id", post.getId());
        postDocument.append("userid", post.getUserId());
        postDocument.append("content", post.getContent());
        postDocument.append("title", post.getTitle());
        postDocument.append("blocked", post.isBlocked() ? "true" : "false");
        postDocument.append("datecreated", post.getDateCreated());
        postDocument.append("likes", post.getLikes());
        postDocument.append("dislikes", post.getDisLikes());

        collection.insertOne(postDocument);
        mongoClient.close();

        return true;
    }

    public boolean saveAds(Advertisements advertisement) {
        MongoClient mongoClient = MongoClients.create(MONGODB_URL);
        MongoDatabase database = mongoClient.getDatabase("Project");
        MongoCollection<Document> collection = database.getCollection("advertisement");

        Document adsDocument = new Document();
        adsDocument.append("id", advertisement.getId());
        adsDocument.append("userid", advertisement.getUserId());
        adsDocument.append("content", new Binary(advertisement.getContent()));
        adsDocument.append("views", advertisement.getViews());
        adsDocument.append("viewspot", advertisement.getViewSpot());
        adsDocument.append("subscriptionenddate", advertisement.getSubscriptionEndDate());

        collection.insertOne(adsDocument);
        mongoClient.close();

        return true;
    }
}