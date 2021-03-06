package org.fran.cloud.azure.storage.service.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fran
 * @Description
 * @Date 2019/1/2 10:16
 */
public class MediaType {
    private static Map<String, String> mediaTypeList = new HashMap<String, String>();
    public static String DEFAULT = "application/octet-stream";

    static {
        mediaTypeList.put(".tif", "image/tiff");
        mediaTypeList.put(".001", "application/x-001");
        mediaTypeList.put(".301", "application/x-301");
        mediaTypeList.put(".323", "text/h323");
        mediaTypeList.put(".906", "application/x-906");
        mediaTypeList.put(".907", "drawing/907");
        mediaTypeList.put(".a11", "application/x-a11");
        mediaTypeList.put(".acp", "audio/x-mei-aac");
        mediaTypeList.put(".ai", "application/postscript");
        mediaTypeList.put(".aif", "audio/aiff");
        mediaTypeList.put(".aifc", "audio/aiff");
        mediaTypeList.put(".aiff", "audio/aiff");
        mediaTypeList.put(".anv", "application/x-anv");
        mediaTypeList.put(".asa", "text/asa");
        mediaTypeList.put(".asf", "video/x-ms-asf");
        mediaTypeList.put(".asp", "text/asp");
        mediaTypeList.put(".asx", "video/x-ms-asf");
        mediaTypeList.put(".au", "audio/basic");
        mediaTypeList.put(".avi", "video/avi");
        mediaTypeList.put(".awf", "application/vnd.adobe.workflow");
        mediaTypeList.put(".biz", "text/xml");
        mediaTypeList.put(".bmp", "application/x-bmp");
        mediaTypeList.put(".bot", "application/x-bot");
        mediaTypeList.put(".c4t", "application/x-c4t");
        mediaTypeList.put(".c90", "application/x-c90");
        mediaTypeList.put(".cal", "application/x-cals");
        mediaTypeList.put(".cat", "application/vnd.ms-pki.seccat");
        mediaTypeList.put(".cdf", "application/x-netcdf");
        mediaTypeList.put(".cdr", "application/x-cdr");
        mediaTypeList.put(".cel", "application/x-cel");
        mediaTypeList.put(".cer", "application/x-x509-ca-cert");
        mediaTypeList.put(".cg4", "application/x-g4");
        mediaTypeList.put(".cgm", "application/x-cgm");
        mediaTypeList.put(".cit", "application/x-cit");
        mediaTypeList.put(".class", "java/*");
        mediaTypeList.put(".cml", "text/xml");
        mediaTypeList.put(".cmp", "application/x-cmp");
        mediaTypeList.put(".cmx", "application/x-cmx");
        mediaTypeList.put(".cot", "application/x-cot");
        mediaTypeList.put(".crl", "application/pkix-crl");
        mediaTypeList.put(".crt", "application/x-x509-ca-cert");
        mediaTypeList.put(".csi", "application/x-csi");
        mediaTypeList.put(".css", "text/css");
        mediaTypeList.put(".cut", "application/x-cut");
        mediaTypeList.put(".dbf", "application/x-dbf");
        mediaTypeList.put(".dbm", "application/x-dbm");
        mediaTypeList.put(".dbx", "application/x-dbx");
        mediaTypeList.put(".dcd", "text/xml");
        mediaTypeList.put(".dcx", "application/x-dcx");
        mediaTypeList.put(".der", "application/x-x509-ca-cert");
        mediaTypeList.put(".dgn", "application/x-dgn");
        mediaTypeList.put(".dib", "application/x-dib");
        mediaTypeList.put(".dll", "application/x-msdownload");
        mediaTypeList.put(".doc", "application/msword");
        mediaTypeList.put(".dot", "application/msword");
        mediaTypeList.put(".drw", "application/x-drw");
        mediaTypeList.put(".dtd", "text/xml");
        mediaTypeList.put(".dwf", "Model/vnd.dwf");
        mediaTypeList.put(".dwf", "application/x-dwf");
        mediaTypeList.put(".dwg", "application/x-dwg");
        mediaTypeList.put(".dxb", "application/x-dxb");
        mediaTypeList.put(".dxf", "application/x-dxf");
        mediaTypeList.put(".edn", "application/vnd.adobe.edn");
        mediaTypeList.put(".emf", "application/x-emf");
        mediaTypeList.put(".eml", "message/rfc822");
        mediaTypeList.put(".ent", "text/xml");
        mediaTypeList.put(".epi", "application/x-epi");
        mediaTypeList.put(".eps", "application/x-ps");
        mediaTypeList.put(".eps", "application/postscript");
        mediaTypeList.put(".etd", "application/x-ebx");
        mediaTypeList.put(".exe", "application/x-msdownload");
        mediaTypeList.put(".fax", "image/fax");
        mediaTypeList.put(".fdf", "application/vnd.fdf");
        mediaTypeList.put(".fif", "application/fractals");
        mediaTypeList.put(".fo", "text/xml");
        mediaTypeList.put(".frm", "application/x-frm");
        mediaTypeList.put(".g4", "application/x-g4");
        mediaTypeList.put(".gbr", "application/x-gbr");
        mediaTypeList.put(".gif", "image/gif");
        mediaTypeList.put(".gl2", "application/x-gl2");
        mediaTypeList.put(".gp4", "application/x-gp4");
        mediaTypeList.put(".hgl", "application/x-hgl");
        mediaTypeList.put(".hmr", "application/x-hmr");
        mediaTypeList.put(".hpg", "application/x-hpgl");
        mediaTypeList.put(".hpl", "application/x-hpl");
        mediaTypeList.put(".hqx", "application/mac-binhex40");
        mediaTypeList.put(".hrf", "application/x-hrf");
        mediaTypeList.put(".hta", "application/hta");
        mediaTypeList.put(".htc", "text/x-component");
        mediaTypeList.put(".htm", "text/html");
        mediaTypeList.put(".html", "text/html");
        mediaTypeList.put(".htt", "text/webviewhtml");
        mediaTypeList.put(".htx", "text/html");
        mediaTypeList.put(".icb", "application/x-icb");
        mediaTypeList.put(".ico", "image/x-icon");
        mediaTypeList.put(".ico", "application/x-ico");
        mediaTypeList.put(".iff", "application/x-iff");
        mediaTypeList.put(".ig4", "application/x-g4");
        mediaTypeList.put(".igs", "application/x-igs");
        mediaTypeList.put(".iii", "application/x-iphone");
        mediaTypeList.put(".img", "application/x-img");
        mediaTypeList.put(".ins", "application/x-internet-signup");
        mediaTypeList.put(".isp", "application/x-internet-signup");
        mediaTypeList.put(".IVF", "video/x-ivf");
        mediaTypeList.put(".java", "java/*");
        mediaTypeList.put(".jfif", "image/jpeg");
        mediaTypeList.put(".jpe", "image/jpeg");
//        mediaTypeList.put(".jpe", "application/x-jpe");
        mediaTypeList.put(".jpeg", "image/jpeg");
        mediaTypeList.put(".jpg", "image/jpeg");
//        mediaTypeList.put(".jpg", "application/x-jpg");
        mediaTypeList.put(".js", "application/x-javascript");
        mediaTypeList.put(".jsp", "text/html");
        mediaTypeList.put(".la1", "audio/x-liquid-file");
        mediaTypeList.put(".lar", "application/x-laplayer-reg");
        mediaTypeList.put(".latex", "application/x-latex");
        mediaTypeList.put(".lavs", "audio/x-liquid-secure");
        mediaTypeList.put(".lbm", "application/x-lbm");
        mediaTypeList.put(".lmsff", "audio/x-la-lms");
        mediaTypeList.put(".ls", "application/x-javascript");
        mediaTypeList.put(".ltr", "application/x-ltr");
        mediaTypeList.put(".m1v", "video/x-mpeg");
        mediaTypeList.put(".m2v", "video/x-mpeg");
        mediaTypeList.put(".m3u", "audio/mpegurl");
        mediaTypeList.put(".m3u8", "application/vnd.apple.mpegurl");
        mediaTypeList.put(".m4e", "video/mpeg4");
        mediaTypeList.put(".mac", "application/x-mac");
        mediaTypeList.put(".man", "application/x-troff-man");
        mediaTypeList.put(".math", "text/xml");
        mediaTypeList.put(".mdb", "application/msaccess");
//        mediaTypeList.put(".mdb", "application/x-mdb");
        mediaTypeList.put(".mfp", "application/x-shockwave-flash");
        mediaTypeList.put(".mht", "message/rfc822");
        mediaTypeList.put(".mhtml", "message/rfc822");
        mediaTypeList.put(".mi", "application/x-mi");
        mediaTypeList.put(".mid", "audio/mid");
        mediaTypeList.put(".midi", "audio/mid");
        mediaTypeList.put(".mil", "application/x-mil");
        mediaTypeList.put(".mml", "text/xml");
        mediaTypeList.put(".mnd", "audio/x-musicnet-download");
        mediaTypeList.put(".mns", "audio/x-musicnet-stream");
        mediaTypeList.put(".mocha", "application/x-javascript");
        mediaTypeList.put(".movie", "video/x-sgi-movie");
        mediaTypeList.put(".mp1", "audio/mp1");
        mediaTypeList.put(".mp2", "audio/mp2");
        mediaTypeList.put(".mp2v", "video/mpeg");
        mediaTypeList.put(".mp3", "audio/mp3");
        mediaTypeList.put(".mp4", "video/mp4");
        mediaTypeList.put(".mpa", "video/x-mpg");
        mediaTypeList.put(".mpd", "application/vnd.ms-project");
        mediaTypeList.put(".mpe", "video/x-mpeg");
        mediaTypeList.put(".mpeg", "video/mpg");
        mediaTypeList.put(".mpg", "video/mpg");
        mediaTypeList.put(".mpga", "audio/rn-mpeg");
        mediaTypeList.put(".mpp", "application/vnd.ms-project");
        mediaTypeList.put(".mps", "video/x-mpeg");
        mediaTypeList.put(".mpt", "application/vnd.ms-project");
        mediaTypeList.put(".mpv", "video/mpg");
        mediaTypeList.put(".mpv2", "video/mpeg");
        mediaTypeList.put(".mpw", "application/vnd.ms-project");
        mediaTypeList.put(".mpx", "application/vnd.ms-project");
        mediaTypeList.put(".mtx", "text/xml");
        mediaTypeList.put(".mxp", "application/x-mmxp");
        mediaTypeList.put(".net", "image/pnetvue");
        mediaTypeList.put(".nrf", "application/x-nrf");
        mediaTypeList.put(".nws", "message/rfc822");
        mediaTypeList.put(".odc", "text/x-ms-odc");
        mediaTypeList.put(".out", "application/x-out");
        mediaTypeList.put(".p10", "application/pkcs10");
        mediaTypeList.put(".p12", "application/x-pkcs12");
        mediaTypeList.put(".p7b", "application/x-pkcs7-certificates");
        mediaTypeList.put(".p7c", "application/pkcs7-mime");
        mediaTypeList.put(".p7m", "application/pkcs7-mime");
        mediaTypeList.put(".p7r", "application/x-pkcs7-certreqresp");
        mediaTypeList.put(".p7s", "application/pkcs7-signature");
        mediaTypeList.put(".pc5", "application/x-pc5");
        mediaTypeList.put(".pci", "application/x-pci");
        mediaTypeList.put(".pcl", "application/x-pcl");
        mediaTypeList.put(".pcx", "application/x-pcx");
        mediaTypeList.put(".pdf", "application/pdf");
        mediaTypeList.put(".pdx", "application/vnd.adobe.pdx");
        mediaTypeList.put(".pfx", "application/x-pkcs12");
        mediaTypeList.put(".pgl", "application/x-pgl");
        mediaTypeList.put(".pic", "application/x-pic");
        mediaTypeList.put(".pko", "application/vnd.ms-pki.pko");
        mediaTypeList.put(".pl", "application/x-perl");
        mediaTypeList.put(".plg", "text/html");
        mediaTypeList.put(".pls", "audio/scpls");
        mediaTypeList.put(".plt", "application/x-plt");
        mediaTypeList.put(".png", "image/png");
//        mediaTypeList.put(".png", "application/x-png");
        mediaTypeList.put(".pot", "application/vnd.ms-powerpoint");
        mediaTypeList.put(".ppa", "application/vnd.ms-powerpoint");
        mediaTypeList.put(".ppm", "application/x-ppm");
        mediaTypeList.put(".pps", "application/vnd.ms-powerpoint");
        mediaTypeList.put(".ppt", "application/vnd.ms-powerpoint");
        mediaTypeList.put(".ppt", "application/x-ppt");
        mediaTypeList.put(".pr", "application/x-pr");
        mediaTypeList.put(".prf", "application/pics-rules");
        mediaTypeList.put(".prn", "application/x-prn");
        mediaTypeList.put(".prt", "application/x-prt");
//        mediaTypeList.put(".ps", "application/x-ps");
        mediaTypeList.put(".ps", "application/postscript");
        mediaTypeList.put(".ptn", "application/x-ptn");
        mediaTypeList.put(".pwz", "application/vnd.ms-powerpoint");
        mediaTypeList.put(".r3t", "text/vnd.rn-realtext3d");
        mediaTypeList.put(".ra", "audio/vnd.rn-realaudio");
        mediaTypeList.put(".ram", "audio/x-pn-realaudio");
        mediaTypeList.put(".ras", "application/x-ras");
        mediaTypeList.put(".rat", "application/rat-file");
        mediaTypeList.put(".rdf", "text/xml");
        mediaTypeList.put(".rec", "application/vnd.rn-recording");
        mediaTypeList.put(".red", "application/x-red");
        mediaTypeList.put(".rgb", "application/x-rgb");
        mediaTypeList.put(".rjs", "application/vnd.rn-realsystem-rjs");
        mediaTypeList.put(".rjt", "application/vnd.rn-realsystem-rjt");
        mediaTypeList.put(".rlc", "application/x-rlc");
        mediaTypeList.put(".rle", "application/x-rle");
        mediaTypeList.put(".rm", "application/vnd.rn-realmedia");
        mediaTypeList.put(".rmf", "application/vnd.adobe.rmf");
        mediaTypeList.put(".rmi", "audio/mid");
//        mediaTypeList.put(".rmj", "application/vnd.rn-realsystem-rmj");
        mediaTypeList.put(".rmm", "audio/x-pn-realaudio");
        mediaTypeList.put(".rmp", "application/vnd.rn-rn_music_package");
        mediaTypeList.put(".rms", "application/vnd.rn-realmedia-secure");
        mediaTypeList.put(".rmvb", "application/vnd.rn-realmedia-vbr");
        mediaTypeList.put(".rmx", "application/vnd.rn-realsystem-rmx");
        mediaTypeList.put(".rnx", "application/vnd.rn-realplayer");
        mediaTypeList.put(".rp", "image/vnd.rn-realpix");
        mediaTypeList.put(".rpm", "audio/x-pn-realaudio-plugin");
        mediaTypeList.put(".rsml", "application/vnd.rn-rsml");
        mediaTypeList.put(".rt", "text/vnd.rn-realtext");
        mediaTypeList.put(".rtf", "application/msword");
//        mediaTypeList.put(".rtf", "application/x-rtf");
        mediaTypeList.put(".rv", "video/vnd.rn-realvideo");
        mediaTypeList.put(".sam", "application/x-sam");
        mediaTypeList.put(".sat", "application/x-sat");
        mediaTypeList.put(".sdp", "application/sdp");
        mediaTypeList.put(".sdw", "application/x-sdw");
        mediaTypeList.put(".sit", "application/x-stuffit");
        mediaTypeList.put(".slb", "application/x-slb");
        mediaTypeList.put(".sld", "application/x-sld");
        mediaTypeList.put(".slk", "drawing/x-slk");
        mediaTypeList.put(".smi", "application/smil");
        mediaTypeList.put(".smil", "application/smil");
        mediaTypeList.put(".smk", "application/x-smk");
        mediaTypeList.put(".snd", "audio/basic");
        mediaTypeList.put(".sol", "text/plain");
        mediaTypeList.put(".sor", "text/plain");
        mediaTypeList.put(".spc", "application/x-pkcs7-certificates");
        mediaTypeList.put(".spl", "application/futuresplash");
        mediaTypeList.put(".spp", "text/xml");
        mediaTypeList.put(".ssm", "application/streamingmedia");
        mediaTypeList.put(".sst", "application/vnd.ms-pki.certstore");
        mediaTypeList.put(".stl", "application/vnd.ms-pki.stl");
        mediaTypeList.put(".stm", "text/html");
        mediaTypeList.put(".sty", "application/x-sty");
        mediaTypeList.put(".svg", "text/xml");
        mediaTypeList.put(".swf", "application/x-shockwave-flash");
        mediaTypeList.put(".tdf", "application/x-tdf");
        mediaTypeList.put(".tg4", "application/x-tg4");
        mediaTypeList.put(".tga", "application/x-tga");
        mediaTypeList.put(".tif", "image/tiff");
//        mediaTypeList.put(".tif", "application/x-tif");
        mediaTypeList.put(".tiff", "image/tiff");
        mediaTypeList.put(".tld", "text/xml");
        mediaTypeList.put(".top", "drawing/x-top");
        mediaTypeList.put(".torrent", "application/x-bittorrent");
        mediaTypeList.put(".ts", "video/vnd.dlna.mpeg-tts");
        mediaTypeList.put(".tsd", "text/xml");
        mediaTypeList.put(".txt", "text/plain");
        mediaTypeList.put(".uin", "application/x-icq");
        mediaTypeList.put(".uls", "text/iuls");
        mediaTypeList.put(".vcf", "text/x-vcard");
        mediaTypeList.put(".vda", "application/x-vda");
        mediaTypeList.put(".vdx", "application/vnd.visio");
        mediaTypeList.put(".vml", "text/xml");
        mediaTypeList.put(".vpg", "application/x-vpeg005");
        mediaTypeList.put(".vsd", "application/vnd.visio");
//        mediaTypeList.put(".vsd", "application/x-vsd");
        mediaTypeList.put(".vss", "application/vnd.visio");
        mediaTypeList.put(".vst", "application/vnd.visio");
//        mediaTypeList.put(".vst", "application/x-vst");
        mediaTypeList.put(".vsw", "application/vnd.visio");
        mediaTypeList.put(".vsx", "application/vnd.visio");
        mediaTypeList.put(".vtx", "application/vnd.visio");
        mediaTypeList.put(".vxml", "text/xml");
        mediaTypeList.put(".wav", "audio/wav");
        mediaTypeList.put(".wax", "audio/x-ms-wax");
        mediaTypeList.put(".wb1", "application/x-wb1");
        mediaTypeList.put(".wb2", "application/x-wb2");
        mediaTypeList.put(".wb3", "application/x-wb3");
        mediaTypeList.put(".wbmp", "image/vnd.wap.wbmp");
        mediaTypeList.put(".wiz", "application/msword");
        mediaTypeList.put(".wk3", "application/x-wk3");
        mediaTypeList.put(".wk4", "application/x-wk4");
        mediaTypeList.put(".wkq", "application/x-wkq");
        mediaTypeList.put(".wks", "application/x-wks");
        mediaTypeList.put(".wm", "video/x-ms-wm");
        mediaTypeList.put(".wma", "audio/x-ms-wma");
        mediaTypeList.put(".wmd", "application/x-ms-wmd");
        mediaTypeList.put(".wmf", "application/x-wmf");
        mediaTypeList.put(".wml", "text/vnd.wap.wml");
        mediaTypeList.put(".wmv", "video/x-ms-wmv");
        mediaTypeList.put(".wmx", "video/x-ms-wmx");
        mediaTypeList.put(".wmz", "application/x-ms-wmz");
        mediaTypeList.put(".wp6", "application/x-wp6");
        mediaTypeList.put(".wpd", "application/x-wpd");
        mediaTypeList.put(".wpg", "application/x-wpg");
        mediaTypeList.put(".wpl", "application/vnd.ms-wpl");
        mediaTypeList.put(".wq1", "application/x-wq1");
        mediaTypeList.put(".wr1", "application/x-wr1");
        mediaTypeList.put(".wri", "application/x-wri");
        mediaTypeList.put(".wrk", "application/x-wrk");
        mediaTypeList.put(".ws", "application/x-ws");
        mediaTypeList.put(".ws2", "application/x-ws");
        mediaTypeList.put(".wsc", "text/scriptlet");
        mediaTypeList.put(".wsdl", "text/xml");
        mediaTypeList.put(".wvx", "video/x-ms-wvx");
        mediaTypeList.put(".xdp", "application/vnd.adobe.xdp");
        mediaTypeList.put(".xdr", "text/xml");
        mediaTypeList.put(".xfd", "application/vnd.adobe.xfd");
        mediaTypeList.put(".xfdf", "application/vnd.adobe.xfdf");
        mediaTypeList.put(".xhtml", "text/html");
        mediaTypeList.put(".xls", "application/vnd.ms-excel");
//        mediaTypeList.put(".xls", "application/x-xls");
        mediaTypeList.put(".xlw", "application/x-xlw");
        mediaTypeList.put(".xml", "text/xml");
        mediaTypeList.put(".xpl", "audio/scpls");
        mediaTypeList.put(".xq", "text/xml");
        mediaTypeList.put(".xql", "text/xml");
        mediaTypeList.put(".xquery", "text/xml");
        mediaTypeList.put(".xsd", "text/xml");
        mediaTypeList.put(".xsl", "text/xml");
        mediaTypeList.put(".xslt", "text/xml");
        mediaTypeList.put(".xwd", "application/x-xwd");
        mediaTypeList.put(".x_b", "application/x-x_b");
        mediaTypeList.put(".sis", "application/vnd.symbian.install");
        mediaTypeList.put(".sisx", "application/vnd.symbian.install");
        mediaTypeList.put(".x_t", "application/x-x_t");
        mediaTypeList.put(".ipa", "application/vnd.iphone");
        mediaTypeList.put(".apk", "application/vnd.android.package-archive");
        mediaTypeList.put(".xap", "application/x-silverlight-app");
    }

    public static String get(String suffix){
        return mediaTypeList.get(suffix);
    }

    public static String probeMediaType(String fileNameWithSuffix){
        String result = null;
        int index = fileNameWithSuffix.lastIndexOf('.');
        if(index != -1){
            String suffix = fileNameWithSuffix.substring(index);
            result = MediaType.get(suffix);
        }
        if(null == result) {
            result = MediaType.DEFAULT;
        }

        return result;
    }
}