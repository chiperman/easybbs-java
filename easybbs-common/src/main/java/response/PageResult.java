package response;

import lombok.Data;

@Data
public class PageResult<T> {
    private Long totalCount;
    private Integer pageNo;
    private Integer pageSize;
    private Integer pageTotal;
    private T list;
}
