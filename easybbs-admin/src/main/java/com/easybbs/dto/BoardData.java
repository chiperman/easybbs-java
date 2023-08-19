package com.easybbs.dto;
import lombok.Data;
import java.util.List;

@Data
public class BoardData {
    private Integer boardId;
    private Integer pboardId;
    private String boardName;
    private String cover;
    private String boardDesc;
    private Integer sort;
    private Integer postType;
    private List<BoardData> children;
}
