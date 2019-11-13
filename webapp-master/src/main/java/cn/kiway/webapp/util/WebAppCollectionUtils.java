package cn.kiway.webapp.util;




import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.core.util.Assert;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author 刘玉祥
 * @date 2019/9/7 11:20
 */
public class WebAppCollectionUtils extends CollectionUtils {

    /**
     * 拆分list
     *
     * @param origin 需要拆分的List
     * @param size   拆分的每个容器的长度
     * @param <T>    泛型
     * @return 拆分好的List
     */
    public static <T> List<List<T>> split(final List<T> origin, final int size) {
        if (Assert.isEmpty(origin)) {
            return Collections.emptyList();
        }
        int block = (origin.size() + size - 1) / size;
        return IntStream.range(0, block).
                boxed().map(i -> {
            int start = i * size;
            int end = Math.min(start + size, origin.size());
            return origin.subList(start, end);
        }).collect(Collectors.toList());
    }


}
