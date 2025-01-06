package com.travelpartner.user_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.travelpartner.user_service.entity.UserPostEntity;

@Repository
public interface UserPostRepo extends JpaRepository<UserPostEntity, String> {

    @Query(value = """
        SELECT 
            post."id" AS id,
            us."userName" AS userName,
            post."location" AS location,
            post."description" AS description,
            img."profilePicId" AS profilePicId,
            img."profilePicName" AS profilePicName,
            us."id" AS userId,
            (
                SELECT JSON_ARRAYAGG(
                    JSON_OBJECT(
                        'postFileName' VALUE imgs."postFileName",
                        'postFieldId' VALUE imgs."postFieldId"
                    )
                )
                FROM "tp_post_images" imgs 
                WHERE imgs."postId" = post."id"
                GROUP BY imgs."postId"
            ) AS postImages,
            (SELECT COUNT(plike."id") FROM "tp_post_like" plike WHERE post."id" = plike."postId" AND plike."isLike" = 1) AS likesCount,
            (SELECT COUNT(pcomment."id") FROM "tp_post_comment" pcomment WHERE post."id" = pcomment."postId") AS commentsCount
        FROM 
            "tp_post" post
        LEFT JOIN 
            "tp_user" us ON us."id" = post."userId"
        LEFT JOIN 
            "tp_image" img ON img."userId" = us."id"
        WHERE 
            us."isRegistered" = 1 
            AND us."isInvited" = 1 
            AND us."inviteOn" IS NOT NULL
        """, nativeQuery = true)
    List<Object[]> fetchUserPosts();

}
