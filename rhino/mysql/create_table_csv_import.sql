create table IF NOT EXISTS csv_import (
  ID            INT(10) PRIMARY KEY,
  REQUEST       VARCHAR(32),
  DATAPAKID     TINYINT,
  RECORD        INT,
  BIC_EVM_GUID  VARCHAR(32),
  EH_GUID       VARCHAR(32),
  BIC_ARTICLE   BIGINT,
  BIC_WORKCTR   INT,
  BIC_DEVUSRID  VARCHAR(16),
  BIC_EXTEVENT  VARCHAR(32),
  BIC_MANIFEST  VARCHAR(32),
  BIC_MERLOCID  VARCHAR(16),
  BIC_PRODUCT   INT,
  BIC_SUBPROD   INT,
  RECORDMODE    VARCHAR(8),
  BIC_DATETIMEL BIGINT,
  BIC_SERVICE   INT,
  BIC_DEVICESRC VARCHAR(32),
  BIC_SENDERID  VARCHAR(32),
  BIC_WCCTYPE   VARCHAR(8),
  BIC_DRV1STACC VARCHAR(8),
  BIC_DRV1STDEL VARCHAR(8),
  BIC_DEL1STDT  SMALLINT,
  BIC_ACC1STDT  SMALLINT,
  BIC_ODPEVTFLG VARCHAR(8),
  BIC_CON1STACC VARCHAR(8),
  BIC_CON1STDEL VARCHAR(8),
  BIC_WC1STACC  VARCHAR(8),
  BIC_WC1STDEL  VARCHAR(8),
  BIC_ACQUITFAC VARCHAR(8),
  BIC_CONTRACT  VARCHAR(8),
  BIC_SIGNATURE VARCHAR(8),
  BIC_COMMS     VARCHAR(8),
  BIC_CONSIGN   VARCHAR(8),
  BIC_ROUNDNO   VARCHAR(8),
  BIC_DEVICEID  VARCHAR(32),
  EVENT_CD      VARCHAR(32),
  BIC_PARCELCNT TINYINT,
  BIC_BULKDMID  VARCHAR(8),
  BIC_SCANCAT1  VARCHAR(8),
  BIC_SCANCAT2  VARCHAR(8),
  BIC_SCANCAT3  VARCHAR(8),
  BIC_SCANQTY1  TINYINT,
  BIC_SCANQTY2  TINYINT,
  BIC_SCANQTY3  TINYINT,
  BIC_CONSJID   VARCHAR(8),
  BIC_LOD_DATE  INT,
  BIC_WCLODGE   VARCHAR(8),
  BIC_DUE_DATE  INT,
  BIC_IN_DUE_DT INT,
  BIC_CUSTREC   VARCHAR(8),
  BIC_SUBURBREC VARCHAR(8),
  BIC_PCODEREC  VARCHAR(8),
  BIC_STOP_CK   VARCHAR(8),
  BIC_STOP_TIME INT,
  BIC_STOP_DATE INT,
  BIC_STOP_SRC  VARCHAR(8),
  BIC_PCODEFROM VARCHAR(8),
  BIC_INTT_STA  VARCHAR(8),
  BIC_EXTT_STA  VARCHAR(8),
  BIC_DELEVENT  VARCHAR(8),
  BIC_HELDEVENT VARCHAR(8),
  BIC_DELDATE   INT,
  BIC_PCODETO   VARCHAR(8),
  BIC_COMMREAS  VARCHAR(8),
  BIC_DRVTMSLOT VARCHAR(8),
  BIC_EVNTDTLOC INT,
  BIC_EVNTTMLOC INT,
  BIC_MSGDTLOC  INT,
  BIC_MSGTMLOC  INT,
  BIC_EVNTDTUTC INT,
  BIC_EVNTTMUTC INT,
  BIC_MSGDTUTC  INT,
  BIC_MSGTMUTC  INT,
  BIC_RT_COL_DT TINYINT,
  BIC_APRTDELEV VARCHAR(8),
  BIC_LATETRANS VARCHAR(8),
  BIC_DISDATTM  VARCHAR(32),
  BIC_DISDTMSG  VARCHAR(32),
  BIC_TIMETABLE VARCHAR(8),
  BIC_DLVRNTWRK INT,
  BIC_FACNART   VARCHAR(8),
  BIC_SCCSPDEL  VARCHAR(8),
  BIC_SCCDPDEL  VARCHAR(8),
  BIC_SCUPLDEL  VARCHAR(8),
  BIC_TASEVENT  VARCHAR(8),
  BIC_TAUEVENT  VARCHAR(8),
  BIC_CRDEVENT  VARCHAR(8),
  BIC_UNDEVENT  VARCHAR(8),
  BIC_DAMEVENT  VARCHAR(8),
  BIC_MISEVENT  VARCHAR(8),
  BIC_RTSEVENT  VARCHAR(8),
  BIC_TPEVENT   VARCHAR(8),
  BIC_MFSTLEVNT VARCHAR(8),
  BIC_MFSTAEVNT VARCHAR(8),
  BIC_LODEVENT  VARCHAR(8),
  BIC_ACEVENT   VARCHAR(8),
  BIC_PSEVENT   VARCHAR(8),
  BIC_MFSTLODDT INT,
  BIC_MFSTLODTM INT,
  BIC_MFSTACMDT INT,
  BIC_MFSTACMTM INT,
  BIC_SCDELDATE INT,
  BIC_SCDELDAY1 INT,
  BIC_SCDELDAY2 INT,
  BIC_SCDELDAY3 INT,
  BIC_SCDELDAY4 INT,
  BIC_SCDELDAY5 INT,
  BIC_SCDELDAY6 INT,
  BIC_SCDELDAY7 INT,
  BIC_SCEMBARGO INT,
  BIC_SCNDLDY1  INT,
  BIC_SCNDLDY2  INT,
  BIC_SCNDLDY3  INT,
  BIC_SCNDLDY4  INT,
  BIC_SCNDLDY5  INT,
  BIC_SCNDLDY6  INT,
  BIC_SCNDLDY7  INT,
  BIC_SCDELTM1  INT,
  BIC_SCDELTM2  INT,
  BIC_SCDELTM3  INT,
  BIC_SCDELTM4  INT,
  BIC_SCDELTM5  INT,
  BIC_SCDELTM6  INT,
  BIC_SCDELTM7  INT,
  BIC_SCREDIR   INT,
  BIC_SCSURVEY  INT,
  BIC_EXPDELDT  INT,
  BIC_PROCDATE  INT,
  BIC_EHMSEQNBR INT,
  BIC_MSGRECDTE INT,
  BIC_MSGRECTME INT,
  BIC_EVENTDATE INT,
  BIC_EVENTTIME INT,
  BIC_ORIGLOC   VARCHAR(8),
  BIC_DESTLOC   VARCHAR(8),
  BIC_LODTMSTMP INT,
  BIC_FACACCEV  VARCHAR(8),
  BIC_FACACDT   INT,
  BIC_WCFACAC   VARCHAR(8),
  BIC_USERROLE  VARCHAR(8),
  BIC_WCCROUND  VARCHAR(8),
  BIC_BPARTNER  VARCHAR(8),
  BIC_UNSUITRSN VARCHAR(8),
  BIC_CALLHDATE INT,
  BIC_CALLHTIME INT,
  BIC_CALLHEVNT VARCHAR(8),
  BIC_FACACTM   INT,
  BIC_TTPNAME   VARCHAR(8),
  BIC_TTPTIME   INT,
  BIC_TTPDATE   INT,
  BIC_CALHREASN VARCHAR(8),
  BIC_ATPDELEVT VARCHAR(8),
  BIC_ATPDELDT  VARCHAR(32),
  BIC_TTPWRKCTR VARCHAR(8),
  BIC_CALWRKCTR VARCHAR(8),
  BIC_RSNCODET1 VARCHAR(16),
  BIC_RSNCODET2 VARCHAR(16),
  BIC_XLSERCODE VARCHAR(8),
  BIC_ARTICORG  VARCHAR(8),
  BIC_FREETXT1  VARCHAR(8),
  BIC_FREETXT2  VARCHAR(8),
  BIC_RTAUTCODE VARCHAR(8),
  BIC_ARTCONDES VARCHAR(8),
  BIC_SNDRSUB   VARCHAR(8),
  BIC_SNDRPCODE VARCHAR(8),
  BIC_SNDRNAME2 VARCHAR(8),
  BIC_SNDRCOMP  VARCHAR(8),
  BIC_SNDRSTATE VARCHAR(8),
  BIC_SNDRCNTRY VARCHAR(8),
  BIC_RECCOMP   VARCHAR(8),
  BIC_DECLENGTH INT,
  BIC_DECHEIGHT INT,
  BIC_DECWIDTH  INT,
  BIC_DECWEIGHT INT,
  BIC_DEC_UNIT  VARCHAR(8),
  BIC_RETNCOST  INT,
  BIC_CURRENCY  VARCHAR(8),
  BIC_DECLENUOM VARCHAR(8),
  BIC_DECHGTUOM VARCHAR(8),
  BIC_DECWDTUOM VARCHAR(8),
  BIC_PLSIZE    VARCHAR(8),
  BIC_ARTDELEV  VARCHAR(8),
  BIC_ARTONBEV  VARCHAR(8),
  BIC_ARTPRCEV  VARCHAR(16),
  BIC_ARTACCEV  VARCHAR(16),
  BIC_ARTCRTDT  INT,
  BIC_ARTCRTTM  INT,
  BIC_RTNSOURCE VARCHAR(8),
  BIC_ACTWEIGHT DECIMAL(5,2),
  BIC_ACTHEIGHT DECIMAL(5,1),
  BIC_ACTLENGTH DECIMAL(5,1),
  BIC_ACTWIDTH  DECIMAL(5,1),
  BIC_ACT_UNIT  VARCHAR(8),
  BIC_ACTHGTUOM VARCHAR(8),
  BIC_ACTLENUOM VARCHAR(8),
  BIC_ACTWDTUOM VARCHAR(8),
  BIC_SERVCODE  VARCHAR(8),
  BIC_EXPDATTIM BIGINT,
  BIC_SCRESH    INT,
  BIC_MPAID     VARCHAR(8),
  PARTNO        INT,
  BIC_LFTD_IND  VARCHAR(1),
  BIC_LFTW_IND  VARCHAR(1),
  BIC_COURIER   VARCHAR(8),
  BIC_RECSTATE  VARCHAR(8),
  BIC_RECCNTRY  VARCHAR(32),
  BIC_ADDRLINE1 VARCHAR(64),
  BIC_ADDRLINE2 VARCHAR(64),
  BIC_ADDRLINE3 VARCHAR(64),
  BIC_ADDRLINE4 VARCHAR(64),
  BIC_PCPROCESS VARCHAR(8),
  BIC_PCREASNID VARCHAR(8),
  BIC_PCREASNTX VARCHAR(8),
  BIC_SIGAUTHTY VARCHAR(32),
  BIC_RECDPID   VARCHAR(8),
  BIC_MOBILE    VARCHAR(32),
  BIC_EMAILADDR VARCHAR(32),
  BIC_COUREXTID VARCHAR(16),
  BIC_COUREXTTX VARCHAR(16),
  BIC_AGGSTAT   VARCHAR(64),
  BIC_TRACKTPAD VARCHAR(64),
  BIC_TRACKIDAD VARCHAR(64),
  BIC_TRACKTPRM VARCHAR(64),
  BIC_TRACKIDRM VARCHAR(64),
  BIC_DEAGGLVL  VARCHAR(64),
  BIC_CARRIER   VARCHAR(64),
  BIC_DESTAGG   VARCHAR(64),
  BIC_DEPDATTIM VARCHAR(64)
);
