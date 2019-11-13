package cn.kiway.webapp.converter;

import cn.kiway.webapp.bean.PersonInfo;
import cn.kiway.webapp.model.PersonInfoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author minte
 * @date 2019/9/4 14:45
 */
@Mapper(componentModel = "spring")
public interface PersonInfoConverter extends BaseBeanConverter<PersonInfo,PersonInfoModel> {
    /**
     *PO-->VO
     * @param pojo 对象
     * @return 返回PersonInfoModel试图
     */
    @Mappings({
            @Mapping(source = "person.roleName", target = "roleName"),
    })
    @Override
    PersonInfoModel toVo(PersonInfo pojo);
}
