<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <g:javascript library="application"/>

    <title>${album.title}</title>

</head>


<style type="text/css">

div {
    float:left;
    margin:5px 5px 5px 5px;
    font-family:Tahoma,Verdana,serif;
    color: #333333;
}

.inv {
    margin:0px 5px 5px 0;
    background-color : gray ;
    color : white ;
    padding-left:5px;
    padding-right:7px;
    border-radius: 5px;
}

.rnd {
    margin:0px 5px 5px 0;
    background-color : white ;
    color : black ;
    padding-left:5px;
    padding-right:7px;
    border-radius: 5px;
}

label {
    width:80px;
    float:left;
}

input, select, textarea {
    font-family: Verdana, Arial, Helvetica, serif;
    font-size:  100%;
}

.album {
    background-color: #ffffff ;
    border-style:solid;
    border-radius: 5px;
    box-shadow:3px 3px 3px #555555;
    border-width:1px;
    border-color:gray;
    padding: 0 0;
}

.thumb {
    border-style:solid;
    border-width:1px;
    border-color:gray;
    padding: 0 0;
}

</style>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.3/jquery.js" type="text/javascript"></script>

<body bgcolor="#444444">

<div style="clear:left; margin-left: 5px;"> <h1><div class="inv"> GGallery </div> <div class="rnd"> ${album.title} </h1> </div> </div>

    <div class="inv" style="clear:left; margin: 10px 10px 10px 10px; padding: 10px 10px 10px 10px;">
        <h3>Share selected images </h3><br/>
        <form action="${request.contextPath}/album/create" method="get">
            <label>Title:</label> <input type="text" name="title" size="25" value="Album Title" class=rnd/><br />
            <label>From:</label> <input type="text" name="from" size="30" value="ggallery@intermobile.ca" /><br />
            <label>Send to:</label> <input type="text" name="to" size="30" value="ggallery@intermobile.ca" /><br />
            <label>Subject:</label> <input type="text" name="subject" size="50" value="Photo Album"/><br />
            <label>Text:</label>
            <textarea name="email" cols="50" rows="3">
Greetings,
Here are a few photos I'd like to share with you.
Enjoy
            </textarea>
            <br />
            <input type="submit" value="Send" />
        </form>
    </div>

    <div >

    <g:each in="${album.photos.sort{ it.filePath }}" var="obj">
        <div class="album">

            <div class="thumb">
                <a href="${obj.url}"> <img SRC="${obj.url}" width="208" height="158" BORDER="0"></a>
            </div>

            <div style="clear:left;">
                ${obj.caption}
            </div>


            <div style="clear:left; margin-left: 5px;" class="inv">
                <input type="checkbox" name="add" value="share" alt="${obj.filePath}" onchange="${remoteFunction(action: 'toggle', params: '\'file=\' + this.alt' )}" /> share

            </div>

        </div>
     </g:each>

    </div>
</body>
</html>




