package mappertest.testConfig

import io.kotest.core.listeners.ProjectListener
import io.kotest.core.spec.AutoScan
import mappertest.testConfig.DataSourceConfiguration.Companion.ORIGINAL_DATABASE_NAME
import mappertest.testConfig.DataSourceConfiguration.Companion.TEST_DATABASE_NAME
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.SingleConnectionDataSource

@AutoScan
class TestDatabaseSchemaListener() : ProjectListener {
    override suspend fun beforeProject() {
        val jdbcTemplate = JdbcTemplate(
            SingleConnectionDataSource(
                "jdbc:mysql://localhost:3306/$ORIGINAL_DATABASE_NAME?useSSL=false",
                "root",
                "mysql",
                false
            )
        )
        jdbcTemplate.execute("CREATE DATABASE IF NOT EXISTS $TEST_DATABASE_NAME")
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS $TEST_DATABASE_NAME.user LIKE $ORIGINAL_DATABASE_NAME.user")
    }

    override suspend fun afterProject() {
        val jdbcTemplate = JdbcTemplate(
            SingleConnectionDataSource(
                "jdbc:mysql://localhost:3306/$ORIGINAL_DATABASE_NAME?useSSL=false",
                "root",
                "mysql",
                false
            )
        )
        jdbcTemplate.execute("DROP DATABASE IF EXISTS $TEST_DATABASE_NAME")
    }
}
