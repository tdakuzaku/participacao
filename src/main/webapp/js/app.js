Vue.http.headers.common['Access-Control-Allow-Origin'] = '*';

var app = new Vue({
  el: '#app',
  data: {
    ready: false,
    alertas: [],
    filtros: {
      tipo: null,
      pontoDeVenda: null
    }
  },
  created: function () {
    this.getAlertas()
  },
  methods: {
    getAlertas: function () {
      this.$http.get('http://localhost:8080/alertas/alertas')
        .then((response) => {
          this.alertas = response.body;
          this.ready = true;
        });
    }
  },
  computed: {
    list() {
      if (!this.filtros.tipo && !this.filtros.pontoDeVenda) {
        return this.alertas;
      }
      return _.filter(this.alertas, alerta => alerta.tipo.indexOf(this.filtro.tipo) >= 0);
    }
  },
})