

```
AWSS3ObjectStorage service = new AWSS3ObjectStorage();
service.setConfig(new AWSS3Config(
        "region",
        "bucket",
        "credentials",
        "accessKeySecret"));
service.init();

ListObjectsResponse res = service.list("editor/", null, null);


//get
service.getObject(
        "news/304d6a4d7a457a6333566d54/share_p.html",
        System.out);

//save
service.saveAndUpdate("test/update.html", RequestBody.of(
        "<html><body><div><H1>TEST!!!</H1></div></body></html>".getBytes()
));


//copy
service.copy(
        "cctvnews-cms-pub-resource",
        "test/copy.html",
        "cctvnews-cms-pub-resource",
        "news/304d6a4d7a457a6333566d54/share_p.html"
);

//delete
service.delete("test/copy.html");
```
