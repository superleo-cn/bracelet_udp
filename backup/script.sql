CREATE TABLE
    tb_health_data
    (
        id bigint NOT NULL AUTO_INCREMENT,
        bracelet_id VARCHAR(40) NOT NULL,
        motion_state VARCHAR(1),
        pulse_state INT,
        temperature FLOAT,
        create_date DATETIME,
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8