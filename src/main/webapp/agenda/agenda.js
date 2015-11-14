
angular.module("agendaApp",[]).controller("agendaCtrl",["$scope","$http","$timeout",function($scope,$http,$timeout){
    // constantes para controle de estado
    $scope.NAVIGATING   = 0;
    $scope.INSERTING    = 1;
    $scope.UPDATING     = 2;
    $scope.DELETING     = 3;
    $scope.SHOWING      = 4;

    // variaveis de trabalho
    $scope.backup = undefined;
    $scope.agenda = {};
    $scope.agendas = [];
    $scope.tipos = [];
    $scope.filtro="";
    $scope.estado=$scope.NAVIGATING;

    // funcao executada ao carregar a tela
    $scope.inicializar = function(){
        $http.get("../ws/agenda/init",{params:$scope.agenda}).success(function(data){
            $scope.tipos = data.tipos;
            $scope.listar();
        }).error(function(){
            alert("ops - erro de comunicação")
        });

    };

    $scope.salvar = function(){
        var idx = $scope.agendas.indexOf($scope.agenda);
        $http.post("../ws/agenda/",$scope.agenda).success(function(data){
            $scope.agenda = data;
            $scope.agendas[idx] = $scope.agenda;
            $scope.estado = $scope.NAVIGATING;
        }).error(function(){
            alert("ops - erro de comunicação")
        });
    };

    $scope.listar = function(){
        $http.get("../ws/agenda/",{params:{filtro:$scope.filtro}}).success(function(data){
            angular.forEach(data,function(value){
               value.nascimento = new Date(value.nascimento);
            });
            $scope.agendas = data;
        }).error(function(){
            alert("ops - erro de comunicação")
        });
    };

    $scope.remover = function(){
        var idx = $scope.agendas.indexOf($scope.agenda);
        $http.delete("../ws/agenda/",{params:{id:$scope.agenda.id}}).success(function(){
            $scope.agendas.splice(idx,1);
            $scope.agenda=undefined;
            $scope.estado = $scope.NAVIGATING;
        }).error(function(){
            alert("ops - erro de comunicação")
        });
    };

    $scope.editar = function(item){
        if($scope.estado==$scope.NAVIGATING) {
            $scope.backup = angular.copy(item);
            $scope.agenda = item;
            $scope.estado = $scope.UPDATING;
            $scope.focus("nome",true);
        }
    };


    $scope.incluir = function(){
        if($scope.estado==$scope.NAVIGATING) {
            $scope.agenda = {};
            $scope.agendas.push($scope.agenda);
            $scope.estado = $scope.INSERTING;
            $scope.focus("nome",false);
        }
    };

    $scope.excluir = function(item){
        if($scope.estado==$scope.NAVIGATING) {
            $scope.agenda = item;
            $scope.estado = $scope.DELETING;
        }
    };

    $scope.mostrar = function(item){
        if($scope.estado==$scope.NAVIGATING) {
            $scope.agenda = item;
            $scope.estado = $scope.SHOWING;
        }
    };


    $scope.marcar = function(item){
        if($scope.estado == $scope.NAVIGATING)
            $scope.agenda = item;
    };

    $scope.cancelar = function(){
        if($scope.estado==$scope.UPDATING) {
            var idx = $scope.agendas.indexOf($scope.agenda);
            $scope.agenda = $scope.backup;
            $scope.agendas[idx]=$scope.agenda;
        }else if($scope.estado==$scope.INSERTING) {
            $scope.agenda = undefined;
            $scope.agendas.pop();
        }
        $scope.estado=$scope.NAVIGATING;
    };

    $scope.limpar = function(){
      $scope.filtro = "";
      $scope.listar();
    };

    // funcao para focar e opcionalmente selecionar o objeto indicado
    // executada apos tudo ter sido atualizado (dentro do $apply)
    $scope.focus = function(nome,select){
      $timeout(function(){
          angular.element("#"+nome)[0].focus();
          if(select)
            angular.element("#"+nome)[0].select();
      });
    };

    $scope.inicializar();

}]);
