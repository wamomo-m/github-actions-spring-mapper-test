package mappertest

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface UserMapper {
    @Select("SELECT name, birthday FROM user WHERE name = #{name}")
    fun selectByName(name: String): List<UserInfo>
}
