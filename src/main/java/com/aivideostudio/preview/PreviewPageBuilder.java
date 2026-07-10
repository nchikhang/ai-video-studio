package com.aivideostudio.preview;

import com.aivideostudio.render.RenderClip;
import com.aivideostudio.render.RenderManifest;

public class PreviewPageBuilder {

    public String build(RenderManifest manifest) {

        StringBuilder html = new StringBuilder();

        html.append("""
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>AI Video Studio Preview</title>

<style>

body{
font-family:Arial;
background:#222;
color:white;
}

table{
width:100%;
border-collapse:collapse;
}

td,th{
border:1px solid #666;
padding:6px;
}

img{
height:90px;
}

.timeline{

position:relative;

height:60px;

background:#444;

margin-top:20px;

}

.clip{

position:absolute;

height:40px;

top:10px;

background:#5c9ded;

border-radius:4px;

}

</style>

</head>

<body>

<h2>Episode Preview</h2>

<table>

<tr>

<th>#</th>

<th>Character</th>

<th>Pose</th>

<th>Background</th>

<th>Start</th>

<th>End</th>

<th>Text</th>

</tr>

""");

        for(RenderClip clip : manifest.getClips()){

            html.append("<tr>");

            html.append("<td>")
                    .append(clip.getIndex())
                    .append("</td>");

            html.append("<td>")
                    .append(clip.getCharacter())
                    .append("</td>");

            html.append("<td>")
                    .append(clip.getPose())
                    .append("</td>");

            html.append("<td>")
                    .append(clip.getBackground())
                    .append("</td>");

            html.append("<td>")
                    .append(String.format("%.2f",clip.getStart()))
                    .append("</td>");

            html.append("<td>")
                    .append(String.format("%.2f",clip.getEnd()))
                    .append("</td>");

            html.append("<td>")
                    .append(clip.getText())
                    .append("</td>");

            html.append("</tr>");

        }

        html.append("""

</table>
<br><br>
""");
        for(RenderClip clip : manifest.getClips()) {
            html.append("<div class=\"timeline\">");
            html.append(String.format("<div class=\"clip\" style=\"left:%spx;width:%spx;\">", clip.getStart() * 120, clip.getDuration() * 120))
                    .append(clip.getCharacter())
                    .append("</div>");
            html.append("</div>");
        }
        html.append("""
</body>

</html>

""");

        return html.toString();

    }

}