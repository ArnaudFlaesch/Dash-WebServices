package com.dash.repository

import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD
import org.springframework.test.context.jdbc.SqlGroup

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SqlGroup(
    Sql(
        statements = [
            "DELETE FROM widget;" +
            "DELETE FROM tab;" +
            "INSERT INTO tab (id, label, tab_order) VALUES ('10', 'News', 1)"
        ], executionPhase = BEFORE_TEST_METHOD
    )
)
annotation class TabDataset
