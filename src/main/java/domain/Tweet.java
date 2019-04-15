/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Yannick
 */
@Entity
@Table(name = "tweets")
@NamedQueries({
    @NamedQuery(name = "tweet.getTweets", query = "SELECT t FROM Tweet t")})
public class Tweet implements Serializable {
    
    @Getter
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String uuid;
    
    @Getter
    @Column(length = 140)
    private String text;
    
    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_uuid")
    private User user;
    
    @Getter
    @Temporal(TemporalType.DATE)
    private Date insertedAt;
    
    @Getter
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name = "tweet_has_likes",
            joinColumns = @JoinColumn(name = "tweet_uuid", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "user_uuid", referencedColumnName = "uuid"))
    private List<User> likes;   
    
    @Getter
    @ManyToMany(mappedBy = "tweets")
    private List<HashTag> hashTags;
    
    public Tweet() {
        this.insertedAt = new Date();
        this.likes = new ArrayList<>();
        this.hashTags = new ArrayList<>();
    }
    
    public Tweet(String text, User user){
        this.text = text;
        this.insertedAt = new Date();
        this.user = user;
        this.likes = new ArrayList<>();
        this.hashTags = new ArrayList<>();
        
        this.user.tweet(this);
    }
      
    public Tweet(String text, User user, Date insertedAt, List<User> likes, List<HashTag> hashTags){
        this.text = text;
        this.insertedAt = insertedAt;
        this.user = user;
        this.likes = likes;
        this.hashTags = hashTags;
        
        this.user.tweet(this);
    }
    
    public void like(User like)
    {
        if (!this.likes.contains(user)) {
            this.likes.add(like);
        } else {
            this.likes.remove(like);
        }
    }
    
//    public int getLikesCount() {
//        return this.likes.size();
//    }
}
