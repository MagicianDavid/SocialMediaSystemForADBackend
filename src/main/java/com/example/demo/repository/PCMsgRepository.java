package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.PCMsg;

public interface PCMsgRepository extends JpaRepository<PCMsg,Integer> {

    @Query("SELECT p FROM PCMsg p ORDER BY p.timeStamp DESC")
    List<PCMsg> findAllPCMsgsOrderByDateDesc();
    
    @Query("SELECT p FROM PCMsg p WHERE p.sourceId IS NULL AND p.user.id = :userId ORDER BY p.timeStamp DESC ")
    List<PCMsg> findAllPostsByUserIdByDateDesc(@Param("userId") int userId);
    
    @Query("SELECT p FROM PCMsg p WHERE p.sourceId = :pcmsgId")
    List<PCMsg> findAllFirstLayerChildren(@Param("pcmsgId") int pcmsgId);
    
    @Query("SELECT p FROM PCMsg p WHERE p.sourceId IS NULL AND p.user.id IN :followingUserIds AND p.user.id NOT IN :blockedUserIds")
    List<PCMsg> findAllFollowingPostsByUserId(@Param("followingUserIds") List<Integer> followingUserIds, 
                                              @Param("blockedUserIds") List<Integer> blockedUserIds);
    
//    @Query("SELECT p FROM PCMsg p WHERE p.sourceId = :sourceId")
//    List<PCMsg> findByPostId(@Param("sourceId") int sourceId);
}