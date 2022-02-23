var res = (function(){
    return{
        connexion  : function(lugar){
            var url = 'localhost:5000/consulta/' + lugar;
            fetch(url, {
                mode: 'no-cors',
                method: 'GET',
                headers: {
                        "Content-type": "application/json",
                        'Access-Control-Allow-Origin': '*'
                         }
            })
                .then(response => response.json())
                .then(json => $('#Resultado').html(json.Resultado))
        }
    }
})();