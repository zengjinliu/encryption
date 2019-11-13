package cn.kiway.webapp.converter;

import cn.kiway.webapp.bean.Person;
import cn.kiway.webapp.bean.PersonInfo;
import cn.kiway.webapp.model.PersonInfoModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-11-13T13:54:25+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"
)
@Component
public class PersonInfoConverterImpl implements PersonInfoConverter {

    @Override
    public List<PersonInfoModel> toVo(List<PersonInfo> pojo) {
        if ( pojo == null ) {
            return null;
        }

        List<PersonInfoModel> list = new ArrayList<PersonInfoModel>( pojo.size() );
        for ( PersonInfo personInfo : pojo ) {
            list.add( toVo( personInfo ) );
        }

        return list;
    }

    @Override
    public PersonInfo toPo(PersonInfoModel vo) {
        if ( vo == null ) {
            return null;
        }

        PersonInfo personInfo = new PersonInfo();

        personInfo.setSeqId( vo.getSeqId() );
        personInfo.setUserId( vo.getUserId() );
        personInfo.setPersonId( vo.getPersonId() );
        personInfo.setUserName( vo.getUserName() );
        personInfo.setPhone( vo.getPhone() );
        personInfo.setWeixinId( vo.getWeixinId() );
        personInfo.setPosition( vo.getPosition() );
        personInfo.setAvatarUrl( vo.getAvatarUrl() );
        personInfo.setCreateDate( vo.getCreateDate() );

        return personInfo;
    }

    @Override
    public List<PersonInfo> toPo(List<PersonInfoModel> vo) {
        if ( vo == null ) {
            return null;
        }

        List<PersonInfo> list = new ArrayList<PersonInfo>( vo.size() );
        for ( PersonInfoModel personInfoModel : vo ) {
            list.add( toPo( personInfoModel ) );
        }

        return list;
    }

    @Override
    public PersonInfoModel toVo(PersonInfo pojo) {
        if ( pojo == null ) {
            return null;
        }

        PersonInfoModel personInfoModel = new PersonInfoModel();

        String roleName = pojoPersonRoleName( pojo );
        if ( roleName != null ) {
            personInfoModel.setRoleName( roleName );
        }
        personInfoModel.setSeqId( pojo.getSeqId() );
        personInfoModel.setUserId( pojo.getUserId() );
        personInfoModel.setPersonId( pojo.getPersonId() );
        personInfoModel.setUserName( pojo.getUserName() );
        personInfoModel.setPhone( pojo.getPhone() );
        personInfoModel.setWeixinId( pojo.getWeixinId() );
        personInfoModel.setPosition( pojo.getPosition() );
        personInfoModel.setAvatarUrl( pojo.getAvatarUrl() );
        personInfoModel.setCreateDate( pojo.getCreateDate() );

        return personInfoModel;
    }

    private String pojoPersonRoleName(PersonInfo personInfo) {
        if ( personInfo == null ) {
            return null;
        }
        Person person = personInfo.getPerson();
        if ( person == null ) {
            return null;
        }
        String roleName = person.getRoleName();
        if ( roleName == null ) {
            return null;
        }
        return roleName;
    }
}
