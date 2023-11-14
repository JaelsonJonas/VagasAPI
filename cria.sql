DROP TABLE TDSS_TB_VAGA;
DROP TABLE TDSS_TB_EMPRESA;

drop SEQUENCE SEQ_E;
drop sequence SEQ_V;

CREATE TABLE TDSS_TB_EMPRESA(
    CD_EMPRESA NUMBER(10) PRIMARY KEY,
    NM_EMPRESA VARCHAR(100) NOT NULL,
    NR_FUNCIONARIO NUMBER 

);
create SEQUENCE SEQ_E INCREMENT by 1 start with 1 nocycle order;

CREATE TABLE TDSS_TB_VAGA (
    CD_VAGA NUMBER(10) PRIMARY KEY,
    DS_TITULO VARCHAR(50) NOT NULL,
    DS_VAGA VARCHAR(255),
    VL_SALARIO NUMBER(10,2),
    DT_PUBLICACAO DATE,
	CD_EMPRESA NUMBER (10),
    FOREIGN KEY (CD_EMPRESA) REFERENCES TDSS_TB_EMPRESA(CD_EMPRESA) ON DELETE CASCADE
);

create SEQUENCE SEQ_V INCREMENT by 1 start with 1 nocycle order;


insert into TDSS_TB_EMPRESA (CD_EMPRESA,nm_empresa,nr_funcionario) VALUES(seq_e.nextval, 'SPECSAI',5);