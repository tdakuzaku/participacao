Vue.http.headers.common['Access-Control-Allow-Origin'] = '*';

var app = new Vue({
  el: '#app',
  data: {
    ready: false,
    alertas: []
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
  }
})