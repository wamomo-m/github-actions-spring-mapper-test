package mappertest.testConfig

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import javax.sql.DataSource

@TestConfiguration
class DataSourceConfiguration {
    @Bean
    fun dataSource(): DataSource = SingleConnectionDataSource(
        "jdbc:mysql://localhost:3306/$ORIGINAL_DATABASE_NAME?useSSL=false",
        "root",
        "mysql",
        false
    )

    @Bean
    fun jdbcTemplate(dataSource: DataSource): JdbcTemplate = JdbcTemplate(dataSource)

    companion object {
        const val ORIGINAL_DATABASE_NAME = "mapper_test"
        const val TEST_DATABASE_NAME = "mapper_test_for_test"
    }
}
