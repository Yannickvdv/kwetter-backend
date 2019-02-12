/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.enums.Language;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yannick
 */
public class UserTest {
    private User user0;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;
    private User user6;
    private User user7;
    private User user8;
    private User user9;
    
    public UserTest() {
        this.user0 = new User("Bert", "Password0", Language.English);
        this.user1 = new User("Henk", "Password1", Language.Dutch);
        this.user2 = new User("Marc", "Password2", Language.English);
        this.user3 = new User("Dennis", "Password3", Language.English);
        this.user4 = new User("Donald Trump", "Password4", Language.Dutch);
        this.user5 = new User("Frank", "Password5", Language.English);
        this.user6 = new User("Sir Lancelot III", "Password6", Language.English);
        this.user7 = new User("Adrianus", "Password7", Language.English);
        this.user8 = new User("JavaScript", "Password8", Language.English);
        this.user9 = new User("Asta", "Password9", Language.Dutch);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testFollowers() {
        List user0Followers = Arrays.asList(this.user8, this.user2);
        user0.addFollower(this.user8);
        user0.addFollower(this.user2);
        assertEquals(user0.getFollowers(), user0Followers);
        
        List user1Followers = Arrays.asList(this.user5, this.user7, this.user9);
        user1.addFollower(this.user5);
        user1.addFollower(this.user7);
        user1.addFollower(this.user9); 
        assertEquals(user1.getFollowers(), user1Followers);
    }
    
    @Test 
    public void testSelfAsFollower() {
        user2.addFollower(this.user2);
        assertEquals(user2.getFollowers(), Arrays.asList());
        
        List expectedUser3Followers = Arrays.asList(this.user8, this.user4);
        user3.addFollower(this.user3);
        user3.addFollower(this.user8);
        user3.addFollower(this.user4);
        assertEquals(user3.getFollowers(), expectedUser3Followers);
    }
}
