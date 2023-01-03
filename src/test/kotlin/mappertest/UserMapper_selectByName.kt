@file:Suppress("ClassName")

package mappertest

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import mappertest.testConfig.DataSourceConfiguration
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate
import java.time.LocalDate

/** [UserMapper.selectByName] のテスト */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DataSourceConfiguration::class)
@MybatisTest
class UserMapper_selectByName(
    private val sut: UserMapper,
    private val jdbcTemplate: JdbcTemplate
) : FreeSpec({
    this as UserMapper_selectByName

    afterTest {
        jdbcTemplate.update("DELETE FROM user")
    }

    "正常系 - name で指定した値にマッチするレコードが取得できること" {
        val name = "taro"
        val date = LocalDate.now()
        val expectedUserInfo = listOf(UserInfo(name = name, birthday = date))

        insertUser(name, date)

        val actualUserInfo = sut.selectByName(name)

        actualUserInfo shouldBe expectedUserInfo
    }
}) {
    private fun insertUser(name: String, birthday: LocalDate) {
        val sql = """
            INSERT INTO user (name, birthday) 
            VALUES ('$name', '$birthday')
        """.trimIndent()
        jdbcTemplate.update(sql)
    }
}
