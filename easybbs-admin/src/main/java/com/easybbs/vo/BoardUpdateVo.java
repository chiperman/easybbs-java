package com.easybbs.vo;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BoardUpdateVo {
    @NotNull(groups = {Delete.class}, message = "boardId不能为空")
    private Integer boardId;
    @NotNull(groups = {Create.class, Update.class}, message = "pBoardId不能为空")
    private Integer pboardId;
    @NotBlank(groups = {Create.class, Update.class}, message = "boardName不能为空")
    private String boardName;
    @NotBlank(groups = {Create.class, Update.class}, message = "boardDesc不能为空")
    private String boardDesc;
    private String cover;

    public @interface Create {}
    public @interface Update {}
    public @interface Delete {}
}
