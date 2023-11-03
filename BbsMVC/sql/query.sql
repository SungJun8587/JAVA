SET @v_DeptId = 0;

# 게시판 페이징 테스트를 위한 임시 데이터 등록
INSERT INTO tblb_bbs(`Subject`, `Content`, `MbIdx`, `Name`, `IP`, `HtmlFlag`, `Hit`, `Recom`, `UnRecom`, `CommentCnt`)
SELECT CONCAT('제목_', `depth`) AS `Subject`, CONCAT('내용_', `depth`) AS `Content`, `depth`, CONCAT('글쓴이_', `depth`) AS `Name`, 
'127.0.0.1' AS `IP`, 1 AS `HtmlFlag`, 0 AS `Hit`, 0 AS `Recom`, 0 AS `UnRecom`, 0 AS `CommentCnt`
FROM
(
	SELECT (@v_DeptId := @v_DeptId + 1) AS `depth`
	FROM INFORMATION_SCHEMA.TABLES AS a
	INNER JOIN INFORMATION_SCHEMA.COLUMNS AS b
	INNER JOIN INFORMATION_SCHEMA.COLUMNS AS c
	LIMIT 3000000
) AS a;


CALL BbsPagingTest(1, 0, 0, '', '', 15, @vOutPrevStepNo, @vOutNextStepNo, @vOutTotBbsCount);
SELECT @vOutPrevStepNo, @vOutNextStepNo, @vOutTotBbsCount; 


INSERT INTO `common`.`tblbm_bbsconfig` (`TbName`, `BbsName`, `BbsInfo`, `BbsClass`, `BbsCount`, `IsCategory`, `IsHtml`, `IsComment`, `IsVote`, `PgSize`, `AttFileCnt`, `APaper`, `CPaper`, `BbsSearchTerm`, `Grant_Html`, `Grant_List`, `Grant_View`, `Grant_Comment`, `Grant_Write`, `Grant_Reply`, `Grant_Delete`, `Grant_Notice`, `IsStatics`, `RegDate`) 
VALUES ('bbs', '일반게시판', '일반게시판 입니다.', '1', '10000000', 1, 1, 1, 1, '15', '0', '30', '30', '0', '10', '10', '10', '10', '10', '10', '10', '10', 1, NOW());


SET @vCurPage = 500000;
SET @vPgSize = 20;
SET @vOffset = (@vCurPage - 1) * @vPgSize;
SET @vNo = 22;

PREPARE prepared_stmt FROM 'SELECT * FROM tblb_bbs WHERE `No` < ? ORDER BY No DESC LIMIT ?'; 
EXECUTE prepared_stmt USING @vNo, @vPgSize;

PREPARE prepared_stmt FROM 'SELECT * FROM tblb_bbs ORDER BY `No` DESC LIMIT ? OFFSET ?'; 
EXECUTE prepared_stmt USING @vPgSize, @vOffset;

ALTER TABLE tblb_bbs AUTO_INCREMENT = 3000001