-- fetch user posts
CREATE OR REPLACE FUNCTION rp_function_fetch_users_posts__v1(
    searchKey IN VARCHAR2
)
RETURN SYS_REFCURSOR IS 
    userPostList SYS_REFCURSOR;

BEGIN
    OPEN userPostList FOR
        SELECT 
            post.id AS id,
            us.USER_NAME AS userName,
            post.LOCATION AS location,
            post.DESCRIPTION AS description,
            img.PROFILEPIC_ID AS profilePicId,
            img.PROFILEPIC_NAME AS profilePicName,
            us.id AS userId,
            (
                SELECT JSON_ARRAYAGG(
                    JSON_OBJECT(
                        'postFileName' VALUE imgs.POST_FILENAME,
                        'postFieldId' VALUE imgs.POST_FILEID
                    )
                )
                FROM tp_post_images imgs 
                WHERE imgs.POST_ID = post.id
                GROUP BY imgs.POST_ID
            ) AS postImages,
            (SELECT COUNT(plike.id) FROM tp_post_like plike WHERE post.id = plike.POST_ID AND plike.IS_LIKE = 1) AS likesCount,
            (SELECT COUNT(pcomment.id) FROM tp_post_comment pcomment WHERE post.id = pcomment.POST_ID) AS commentsCount
        FROM 
           tp_post post
        LEFT JOIN 
            tp_user us ON us.id = post.USER_ID
        LEFT JOIN 
            tp_image img ON img.USER_ID = us.id
        WHERE 
            us.IS_REGISTERED = 1 
            AND us.IS_INVITED = 1 
            AND us.INVITE_ON IS NOT NULL 
            AND post.USER_ID = userId; -- Correctly scoped
    RETURN userPostList;
END;

-- grants for above function

GRANT EXECUTE ON rp_function_fetch_users_posts__v1 TO travelpartner;

---

create or replace NONEDITIONABLE FUNCTION rp_function_fetch_users_posts_v1(
    searchKey IN VARCHAR2 DEFAULT 'null'
)
RETURN SYS_REFCURSOR IS 
    userPostList SYS_REFCURSOR;

BEGIN
    OPEN userPostList FOR
       select b.*,count(*) over() "totalCount" from ( SELECT distinct
            post.id AS "id",
            us.USER_NAME AS "userName",
            post.LOCATION AS "location",
            post.DESCRIPTION AS "description",
            img.PROFILEPIC_ID AS "profilePicId",
            img.PROFILEPIC_NAME AS "profilePicName",
            us.id AS "userId",
            (
                SELECT JSON_ARRAYAGG(
                    JSON_OBJECT(
                        'postFileName' VALUE imgs.POST_FILENAME,
                        'postFieldId' VALUE imgs.POST_FILEID
                    )
                )
                FROM tp_post_images imgs 
                WHERE imgs.POST_ID = post.id
                GROUP BY imgs.POST_ID
            ) AS "postImages",
            (SELECT COUNT(plike.id) FROM tp_post_like plike WHERE post.id = plike.POST_ID AND plike.IS_LIKE = 1) AS "likesCount",
            (SELECT COUNT(pcomment.id) FROM tp_post_comment pcomment WHERE post.id = pcomment.POST_ID) AS "commentsCount"
        FROM 
           tp_post post
        LEFT JOIN 
            tp_user us ON us.id = post.USER_ID
        LEFT JOIN 
            tp_image img ON img.USER_ID = us.id
        WHERE 
            us.IS_REGISTERED = 1 
            AND us.IS_INVITED = 1 
            AND us.INVITE_ON IS NOT NULL 
            )b 
            where case when searchKey is not null and searchKey='null' then 1
            when (
            (LOWER(TRIM("userName")) like '%' || searchKey || '%') or
            (LOWER(TRIM("location")) like '%' || searchKey || '%')) then 1 end=1;
    RETURN userPostList;
END;

GRANT EXECUTE ON rp_function_fetch_users_posts_v1 TO travelpartner;