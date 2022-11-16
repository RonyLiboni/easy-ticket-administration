CREATE TABLE "SHOW_PHOTOS" (
    "SHOW_PHOTO_ID" NUMBER,
    "SHOW_ID" NUMBER, 
    "FILE_NAME" NVARCHAR2(150) NOT NULL,
    "DESCRIPTION" NVARCHAR2(150) NOT NULL,
    "CONTENT_TYPE" NVARCHAR2(50) NOT NULL,
    "FILE_SIZE" NUMBER(9,0) NOT NULL,
    CONSTRAINT "PK_SHOW_PHOTO_ID" PRIMARY KEY ("SHOW_PHOTO_ID"),
    CONSTRAINT "FK_SHOW_MODEL_ID" FOREIGN KEY ("SHOW_ID") REFERENCES "SHOWS"
 );