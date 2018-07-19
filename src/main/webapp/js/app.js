Vue.http.headers.common['Access-Control-Allow-Origin'] = '*';

var app = new Vue({
  el: '#app',
  data: {
    ready: false,
    alertas: [],
    tipos: [],
    pontosDeVendas: [],
    filtros: {
      tipo: '',
      pontoDeVenda: ''
    }
  },
  created: function () {
    this.getAlertas()
    this.getTipos()
    this.getPontosDeVendas()
  },
  methods: {
    getAlertas: function () {
      this.$http.get('http://localhost:8080/alertas/alertas')
        .then((response) => {
          this.alertas = response.body;
          this.ready = true;
        });
    },
    getTipos: function () {
      this.$http.get('http://localhost:8080/alertas/tipos')
        .then((response) => {
          this.tipos = response.body.sort();
        });
    },
    getPontosDeVendas: function () {
      this.$http.get('http://localhost:8080/alertas/pontos_de_vendas')
        .then((response) => {
          this.pontosDeVendas = response.body.sort();
        });
    },
    filtrarAlertas: function () {
      this.$http.get('http://localhost:8080/alertas/buscar', { params: this.filtros })
        .then((response) => {
          this.alertas = response.body.sort();
        });
    }
  }
})