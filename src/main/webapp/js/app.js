Vue.http.headers.common['Access-Control-Allow-Origin'] = '*';

var app = new Vue({
  el: '#app',
  data: {
    alerts: []
  },
  created: function () {
    this.getAlerts()
  },
  methods: {
    getAlerts: function () {
      this.$http.get('http://localhost:8080/alertas/alertas')
        .then((response) => {
          console.log(response)
          // this.alerts = response.data.message;
        });
    }
  }
})