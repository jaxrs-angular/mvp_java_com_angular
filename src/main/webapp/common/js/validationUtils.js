
function cpfEhValido(strCPF) {
    var soma;
    var resto;
    soma = 0;
    while(strCPF.indexOf(".")>=0 ||strCPF.indexOf("-")>=0 )
        strCPF = strCPF.replace(".","").replace("-","");
    if (strCPF == "00000000000")
        return false;
    for (var i=1; i<=9; i++)
        soma = soma + parseInt(strCPF.substring(i-1, i)) * (11 - i);
    resto = (soma * 10) % 11;
    if ((resto == 10) || (resto == 11))
        resto = 0;
    if (resto != parseInt(strCPF.substring(9, 10)) )
        return false;
    soma = 0;
    for (i = 1; i <= 10; i++)
        soma = soma + parseInt(strCPF.substring(i-1, i)) * (12 - i);
    resto = (soma * 10) % 11;
    if ((resto == 10) || (resto == 11))
        resto = 0;

    return  (resto == parseInt(strCPF.substring(10, 11) ) );
}

function isEmpty(scope,ele){
    path = ((ele.attr("ng-model"))?ele.attr("ng-model"):ele.attr("k-ng-model")).split(".");
    value = scope.$eval(path[0]);
    for(var i = 1;i<path.length;i++)
        value = value[path[i]];
    return (value == undefined || value == null ||  (typeof value == "string" && value.trim()=='' ));
}

function asDate(value){
    if (value == "now") {
        return new Date();
    } else if (value == "today") {
        var nvalue = new Date();
        nvalue.setHours(23);
        nvalue.setMinutes(59);
        nvalue.setSeconds(59);
        nvalue.setMinutes(59);
        nvalue.setMilliseconds(999);
        return nvalue;
    } else if(value.indexOf("/")>0){ // formato brasileiro (britsh)
        var v  = value.split(" "); // separa data da hora
        var d = v[0].split("/");
        var h = [0,0,0,0];
        if(v.length==2) {
            h = v[1].split(":");
        }
        for(var x = h.length;x<5;x++){
            h[x-1]=0;
        }
        return new Date(d[2],d[1],d[0],h[0],h[1],h[2],h[3]);
    }else if(value.indexOf("-")>0){
        return new Date(value);
    }
}
