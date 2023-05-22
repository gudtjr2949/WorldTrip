
<template>
  <div class="ui grid">
    <div class="four wide column">
    <form class="ui segment large form">
      <div class="ui segment">
        <div class="field">
          <div class="ui right icon input large">
            <input type="text" v-model="title" ref="autocomplete" />
            <button class="ui button" @click="search">검색</button>
          </div>
        </div>    
      </div>
      <div><button @click="centerMap(curX, curY, $event)">현재 위치</button></div>

        <div class="flex-container">
          <div class="flex-row">
            <button class="custom-button ui button" @click="checkPlace">장소 검색</button>
            <button class="custom-button ui button" @click="checkNearBy">주변 검색</button>
            <button class="custom-button ui button" @click="checkRoad">길찾기</button>
          </div>
          <div class="flex-row">
            <button class="custom-button ui button" @click="checkRoute">경로 찾기</button>
            <button class="custom-button ui button" @click="checkAir">항공권</button>
            <button class="custom-button ui button" @click="checkHotel">호텔</button>
          </div>
        </div>
    </form>

    <div v-if="identifier === 'place'">
      <label>장소를 입력해 주세요</label>
    </div>

    <div v-if="identifier === 'nearby'">
      <label>주변 거리를 선택해 주세요</label>
      <div class="field">
            <div class="field">
              거리 선택
              <select v-model="radius">
                <option value="1">1 KM</option>
                <option value="5">5 KM</option>
                <option value="10">10 KM</option>
                <option value="15">15 KM</option>
                <option value="20">20 KM</option>
              </select>
            </div>
        </div>
    </div>

    <div v-if="identifier === 'route'">
      <div class="field">
          <div class="two fields">
            <div class="field ui right icon input">
              <label>출발점:</label>
                <input type="text" ref="origin">                
            </div>

            <div v-if="origin.length > 0 && identifier === 'route'" class="ui segment" style="max-height: 500px; overflow: scroll">
                <div class="item" v-for="(start, index) in origin" :key="index">
                    <div>{{start.formatted_address}}</div>
                    <button @click="deleteOrigin(index)">삭제</button>
                </div>
            </div>
            
            <div class="field ui right icon input">
              <label>도착점:</label>
                <input type="text" ref="destination">
            </div>
            <div v-if="destination.length > 0 && identifier === 'route'" class="ui segment" style="max-height: 500px; overflow: scroll">
                <div class="item" v-for="(start, index) in origin" :key="index">
                    <div>{{start.formatted_address}}</div>
                    <button @click="deleteDestination(index)">삭제</button>
                </div>
            </div>
          </div>
        </div>

      <button class="ui button" @click="Trans">대중교통</button>
        <button class="ui button" @click="Car">자동차</button>
    </div>

    <div v-if="identifier === 'road'">
      
      <div class="field">
          <div class="two fields">
            <div class="field ui right icon input">
              <label>출발:</label>
                <input type="text" ref="start">
            </div>
            <div class="field ui right icon input">
              <label>도착:</label>
                <input type="text" ref="end">
            </div>
          </div>
        </div>
        <button class="custom-button ui button" @click="checkTrans">경로 검색</button>
        <button class="ui button" @click="byTrans">대중교통</button>
        <button class="ui button" @click="byCar">자동차</button>

    </div>


    

    <div v-if="identifier === 'air'">
      <label>air:</label>
    </div>


    <div v-if="transits.length > 0 && drive === 'bus' && identifier === 'road'" class="ui segment" style="max-height: 500px; overflow: scroll">
      <div class="two fields">
          <div class="field">{{findA}} -> {{findB}}</div>
          <td><button class="ui button" @click="drawTransit">그리기</button></td>                    
        </div>
      <div class="field">
          <div class="ui divided items field">
              <div class="item" v-for="(transit, index) in transits" :key="index">

                <button @click="transit[0].show = !transit[0].show" >상세 보기</button>
                  <div class="content">
                      <div class="meta">{{transit[0].totalDis}}</div>
                      <div class="meta">{{transit[0].totalDur}}</div>
                  </div>
                  <td><button class="ui button">추가</button></td>                    
                    <div v-show="transit[0].show">
                        <div class="item" v-for="(info, i) in transit[0].distance.length" :key="i">
                            <div class="header">{{transit[0].html_instructions[i]}}</div>
                            <div class="meta">{{transit[0].distance[i]}}</div>
                            <div class="meta">{{transit[0].duration[i]}}</div>
                      </div>
                      
                  </div>
                </div>
              </div>              
        </div>
      </div>

      <!-- <div v-if="transits.length < 1 && drive === `bus`" class="ui segment" style="max-height: 500px; overflow: scroll">
        <div class="field">
          검색 결과 없음
        </div>
      </div> -->


      <div v-if="car.length > 0 && drive === 'drive' && identifier === 'road'" class="ui segment" style="max-height: 500px; overflow: scroll">
        <div class="two fields">
          <div class="field">{{findA}} -> {{findB}}</div>
          <td><button class="ui button" @click="drawDriving">그리기</button></td>                    
        </div>
        <div class="field">
          <div class="ui divided items field">
            <div class="item" v-for="(info, index) in car" :key="index">
              <div class="content">
                      <div class="meta">{{info.distance}}</div>
                      <div class="meta">{{info.duration}}</div>
                  </div>
                  <td><button class="ui button">추가</button></td>                    
            </div>
          </div>
        </div>
      </div>

      <!-- <div v-if="car.length < 1 && drive === `drive`" class="ui segment" style="max-height: 500px; overflow: scroll">
        <div class="field">
          검색 결과 없음
        </div>
      </div> -->

      <div v-if="hotels.length > 0 && identifier === 'hotel'" class="ui segment" style="max-height: 500px; overflow: scroll">
          <table >
        <thead >
            <th>사진</th>
            <th>이름</th>
            <th>주소</th>
            <th>추가</th>
        </thead>
        <tbody v-for="hotel in hotels" :key="hotel.id">
          <tr>
            <td>
            <a :href="hotel.url" target="_blank">
              <img :src="hotel.photo" width="50" height="50"/>
            </a>
          </td>
            <td @click="centerMap(hotel.geometry.location.lat, hotel.geometry.location.lng, $event)">{{ hotel.name ? hotel.name : '-'  }}</td>
            <td @click="centerMap(hotel.geometry.location.lat, hotel.geometry.location.lng, $event)">{{ hotel.vicinity ? hotel.vicinity : '-' }}</td>
            <td><button class="ui button" @click=makePlan(hotel)>추가</button></td>
          </tr>
        </tbody>
        </table>
      </div>








<div v-if="places.length > 0 && (identifier === 'nearby' || identifier === 'place')" class="ui segment" style="max-height: 500px; overflow: scroll">
  <table >
    <thead >
        <th>사진</th>
        <th>이름</th>
        <th>주소</th>
        <th>추가</th>
    </thead>
    <tbody v-for="place in places" :key="place.id">
      <tr>
        <td>
        <a :href="place.url" target="_blank">
          <img :src="place.photo" width="50" height="50"/>
        </a>
      </td>
        <td @click="centerMap(place.geometry.location.lat, place.geometry.location.lng, $event)">{{ place.name ? place.name : '-'  }}</td>
        <td @click="centerMap(place.geometry.location.lat, place.geometry.location.lng, $event)">{{ place.vicinity ? place.vicinity : '-' }}</td>
        <td><button class="ui button" @click=makePlan(place)>추가</button></td>
      </tr>
    </tbody>

    </table>
</div>
  </div>
    <div class="eight wide column segment ui" id = "map" ref="map" style="height: 90vh;"></div>

      <div class="four wide column">
        <form class="ui segment large form" ref="myForm">
      
        <h3>user_id 의 두근두근 여행</h3>

        <div class="field">
          <div class="two fields">
            <div class="field">
              <label>Start Date</label>
              <input type="date" id = "start_date" name = "start_date" v-model="start_date" />
            </div>
            <div class="field">
              <label>End Date</label>
              <input type="date" id = "end_date" name = "end_date" v-model="end_date" />
            </div>
          </div>
        </div>
        <div class="field">
            <label style="text-align:left">Title</label>
            <input id = "title" v-model="plan_title" name = "title" type="text"/>
        </div>

        <table v-if="plans.length > 0">
           <thead>
            <th>Places</th>
          </thead>  
        <tbody v-for="plan in plans" :key="plan.id" id="place_id" name="place_id">
          <tr>
            <td>
            <a :href="plan.url" target="_blank">
              <img :src="plan.photo" width="50" height="50"/>
            </a>
          </td>
            <td @click="centerMap(plan.geometry.location.lat, plan.geometry.location.lng, $event)">{{ plan.name ? plan.name : '-'  }}</td>
            <td @click="centerMap(plan.geometry.location.lat, plan.geometry.location.lng, $event)">{{ plan.vicinity ? plan.vicinity : '-' }}</td>
            <td><button class="ui button" @click="deletePlan(plan, $event)">삭제</button></td>
          </tr>
        </tbody>
        </table>
        <div class="field">
          <label style="text-align:left">Content</label>
            <textarea id="content" name="content" v-model="content" style="height: 200px; resize: vertical;"></textarea>
        </div>      
        </form>
        <button class="ui button" @click="submitForm">작성</button>
      </div>
    
  </div>
  
</template>
<script>
import axios from 'axios'

// 장소 검색, 주변 검색, 길 찾기, 최적경로, 항공권, 호텔
export default {

    data(){
        return{
            map:"",
            curX:"",
            curY:"",
            searchX:"",
            searchY:"",
            address:"",
            title:"",
            places:[],
            plans:[],
            start_date:"",
            end_date:"",
            plan_title:"",
            content:"",
            plan_id:"",
            markers:[],
            identifier:"",
            radius:1,
            greenMarkers:[],    
            start:"",
            end:"",       
            transits:[],
            findA:"",
            findB:"",
            drive:"bus",
            car:[],
            hotels:[],
            origin:[],
            destination:[],
            check:"bus",
        }
    },
  watch: {
    // Watch for changes in the form data
    start_date: {
      handler(newValue) {
        this.sendFormData();
      },
      deep: true, // Watch nested properties
    },
    end_date: {
      handler(newValue) {
        this.sendFormData();
      },
      deep: true,
    },
    plan_title: {
      handler(newValue) {
        this.sendFormData();
      },
      deep: true,
    },
    plans: {
      handler(newValue) {
        this.sendFormData();
      },
      deep: true,
    },
    content: {
      handler(newValue) {
        this.sendFormData();
      },
      deep: true,
    },
  },
    computed: {
    coordinates() {
      return `${this.curX}, ${this.curY}`;
      }
    },

    mounted(){
        
        let autocomplete = new google.maps.places.Autocomplete(this.$refs["autocomplete"],{
        bounds: new google.maps.LatLngBounds(
          // new google.maps.LatLng(x, y)
        )
      });

      autocomplete.addListener("place_changed", () =>{
        let place = autocomplete.getPlace();
        console.log(place);
        this.showUserLocationOnTheMap(place.geometry.location.lat(), place.geometry.location.lng())
      });

          this.map = new google.maps.Map(this.$refs['map'], {
          zoom: 15,
          center: new google.maps.LatLng(37.5550368, 126.9707056),
        });
          
        if (navigator.geolocation){
            
            navigator.geolocation.getCurrentPosition(
          position => {
            this.curX = position.coords.latitude;
            this.curY = position.coords.longitude;
            
            this.searchX = position.coords.latitude;
            this.searchY = position.coords.longitude;

          const baseURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
          const location = this.curX + "," + this.curY;
          const radius = 5000;
          const keyword = encodeURIComponent(this.title); // 특수 문자가 포함될 수 있으므로 인코딩
          const apiKey = "AIzaSyC5Wsfp6CnCn4wltag9i5XrDNGvOwipkiY"; // 실제로 사용하는 구글 API 키로 대체해야 합니다.

          const url = `${baseURL}?location=${location}&radius=${radius}&keyword=${keyword}&key=${apiKey}`;
      

          
            console.log(url);
            axios.get(url)
            .then(response => {
                this.address = response.data.results[0].formatted_address;  

                this.map = new google.maps.Map(this.$refs['map'],{
                    zoom:15,
                    center: new google.maps.LatLng(this.curX, this.curY),                    
                });
                new google.maps.Marker({
                    position: new google.maps.LatLng(this.curX, this.curY),                    
                    animation: google.maps.Animation.DROP,                    
                    map: this.map,
                });
            })
          });
        }
                
    },
    methods: {
      deleteOrigin(index){
        this.origin.pop(index);
      },
      deleteDestination(index){
        this.destination.pop(index);
      },
      drawTransit(){
        var directionsService = new google.maps.DirectionsService();
        var directionsRenderer = new google.maps.DirectionsRenderer();

        var map = new google.maps.Map(this.$refs['map']);

        var request = {
          origin: this.findA,
          destination: this.findB,
          travelMode: 'TRANSIT',
        };

        directionsService.route(request, (result, status) => {
          if (status === 'OK') {
            directionsRenderer.setDirections(result);
            directionsRenderer.setMap(map);
          }
        }).catch(error => {
            console.log(error);
        });

      },
      drawDriving(){
        var directionsService = new google.maps.DirectionsService();
        var directionsRenderer = new google.maps.DirectionsRenderer();

        // var map = new google.maps.Map(this.$refs['map']);

        var request = {
          origin: this.findA,
          destination: this.findB,
          travelMode: 'DRIVING',
        };

        directionsService.route(request, (result, status) => {
          if (status === 'OK') {
            directionsRenderer.setDirections(result);
            directionsRenderer.setMap(this.map);
          }
        }).catch(error => {
            console.log(error);
        });

      },

      carRoad(){
        this.car = [];
        this.transits = [];
        const url = `https://maps.googleapis.com/maps/api/directions/json?origin=place_id:${this.start}&destination=place_id:${this.end}&key=AIzaSyC5Wsfp6CnCn4wltag9i5XrDNGvOwipkiY&mode=DRIVING&alternatives=true`

        axios.get(url)
        .then(response => {
          // console.log(response.data.routes);

          let routes = response.data.routes;
          routes.forEach((route) => {
            route.legs[0].distance.text;
            route.legs[0].duration.text;
            this.car.push({
              distance: route.legs[0].distance.text,
              duration: route.legs[0].duration.text
            })
          })
        })
      },
       
      byTrans(){
        this.drive = "bus";
      },

      byCar(){
        this.drive = "drive";
      },

      trans(){
        this.check = "bus";
      },
      car(){
        this.check = "drive"
      },

      checkTrans(){
        if (this.drive === "bus"){
          this.findRoad();
        }else{
          this.carRoad();
        }
      },
      checkRoute(){
        if (this.check === "bus"){
          this.findTrans();
        }else{
          this.findDrive();
        }
      },

      findRoad(){      
        
        this.car = [];
        this.transits = [];
        const url = `https://maps.googleapis.com/maps/api/directions/json?origin=place_id:${this.start}&destination=place_id:${this.end}&mode=transit&key=AIzaSyC5Wsfp6CnCn4wltag9i5XrDNGvOwipkiY&alternatives=true`
                    
        axios.get(url)
            .then(response => {
              const result = response.data;
              if (result.status === 'OK') {
                let temp = result.routes;
                
                temp.forEach((route) => {
                  let transit = [];
                  let road = route.legs[0].steps;
                  let distance = [];
                  let duration = [];
                  let html_instructions = [];
                  var totalDis = route.legs[0].distance.text;
                  var totalDur = route.legs[0].duration.text;
                  
                  road.forEach((info) => {
                    var dis = info.distance.text;
                    var dur = info.duration.text;
                    var html = info.html_instructions;
                                     
                    distance.push(dis);
                    duration.push(dur);
                    html_instructions.push(html);
                  })         
                  
                  transit.push({
                    totalDis: totalDis,
                    totalDur: totalDur,
                    distance: distance,
                    duration: duration,
                    html_instructions: html_instructions,
                    show: false,
                    start: this.findA,
                    end: this.findB
                  })
                  this.transits.push(transit);
                })
                console.log(this.transits);
              } 
            })
            .catch(error => {
              console.error(error);
            });

      },


      centerMap(latitude, longitude, event) {
          event.preventDefault();
          this.searchX = latitude;
          this.searchY = longitude;

          for (let i = 0; i < this.greenMarkers.length; i++) {
              this.greenMarkers[i].setMap(null);
            }
            this.greenMarkers = [];


          if (this.searchX !== this.curX && this.searchY !== this.curY){
             const marker = new google.maps.Marker({
                position: new google.maps.LatLng(this.searchX, this.searchY),
                map: this.map,
                icon: "https://maps.google.com/mapfiles/ms/icons/green-dot.png"
              });
          this.greenMarkers.push(marker); 
          }
          const center = new google.maps.LatLng(latitude, longitude);
          this.map.setCenter(center);
        },


      search(e){
        e.preventDefault();
        switch (this.identifier) {
          case 'place':            
            this.placeSearch();
            break;
          case 'nearby':
            this.nearBySearch();
            break;
          case 'road':
            this.checkTrans();
            break;
          case 'route':
            this.checkRoute();
            break;
        //   case 'air':
        //     // Execute the function for air
        //     doAirFunction();
        //     break;
          default:
            this.placeSearch();
            break;
        }

      },

      findHotel(){
        
        this.clearMarkers();

        this.hotels = [];
        const url = `https://maps.googleapis.com/maps/api/place/nearbysearch/json?keyword=호텔&location=${this.searchX},${this.searchY}&radius=5000&key=AIzaSyC5Wsfp6CnCn4wltag9i5XrDNGvOwipkiY`
        console.log(url)
        console.log(this.searchX);
        console.log(this.searchY);
        axios.get(url).then(reponse => {
            
            var temp = reponse.data.results;
            var idx = 0;
            temp.forEach((place) => {
              axios.get(`http://localhost/plan/api/photo/${place.place_id}`)
              .then(response =>{
                const photos = response.data;
                const url = 'https://maps.googleapis.com/maps/api/place/photo?maxwidth=40&photoreference=' + photos.photo + '&key=AIzaSyC5Wsfp6CnCn4wltag9i5XrDNGvOwipkiY'
                place.photo = url;
                // this.places.push(place);
                axios.get(`https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyC5Wsfp6CnCn4wltag9i5XrDNGvOwipkiY&place_id=` + place.place_id)
                .then(res => {
                  var t = res.data.result;
                  console.log(t.url);
                  place.url = t.url;
                  this.hotels.push(place);

                            // 마커 추가
                  const marker = new google.maps.Marker({
                    position: new google.maps.LatLng(place.geometry.location.lat, place.geometry.location.lng),
                    map: this.map,
                    icon: "https://maps.google.com/mapfiles/ms/icons/blue-dot.png"
                  });

                  marker.addListener('click', () => {
                    const infowindow = new google.maps.InfoWindow({
                      content: `<div><strong>${place.name}</strong><br>${place.vicinity}<br><a href="${place.url}" target="_blank">링크</a></div>`
                    });
                    infowindow.open(this.map, marker);
                  });

                  this.markers.push(marker);
                })
              })
            }
            )

        })



      },


      placeSearch(){
        this.clearMarkers();
        this.places = [];
        const keyword = encodeURIComponent(this.title)
        const url = `https://maps.googleapis.com/maps/api/place/textsearch/json?key=AIzaSyC5Wsfp6CnCn4wltag9i5XrDNGvOwipkiY&query=${keyword}`

        axios.get(url).then(response => {
            console.log(response);            
           var temp = response.data.results;
            // this.places = reponse.data.results;
            if (temp.length > 0){
            var idx = 0;
            temp.forEach((place) => {
              axios.get(`http://localhost/plan/api/photo/${place.place_id}`)
              .then(response =>{
                const photos = response.data;
                const url = 'https://maps.googleapis.com/maps/api/place/photo?maxwidth=40&photoreference=' + photos.photo + '&key=AIzaSyC5Wsfp6CnCn4wltag9i5XrDNGvOwipkiY'
                place.photo = url;
                // this.places.push(place);
                axios.get(`https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyC5Wsfp6CnCn4wltag9i5XrDNGvOwipkiY&place_id=` + place.place_id)
                .then(res => {
                  var t = res.data.result;
                  console.log(t.url);
                  place.url = t.url;
                  this.places.push(place);

                            // 마커 추가
                  const marker = new google.maps.Marker({
                    position: new google.maps.LatLng(place.geometry.location.lat, place.geometry.location.lng),
                    map: this.map,
                    icon: "https://maps.google.com/mapfiles/ms/icons/blue-dot.png"
                  });

                  marker.addListener('click', () => {
                    const infowindow = new google.maps.InfoWindow({
                      content: `<div><strong>${place.name}</strong><br>${place.vicinity}<br><a href="${place.url}" target="_blank">링크</a></div>`
                    });
                    infowindow.open(this.map, marker);
                  });

                  this.markers.push(marker);
                })
              })
            }
            )
            }
            if (temp.length > 0) {
              const firstPlace = temp[0];
              const latLng = new google.maps.LatLng(firstPlace.geometry.location.lat, firstPlace.geometry.location.lng);
              this.map.setCenter(latLng);
            }
        }).catch(error => {
          // 에러가 발생한 경우 대체 문구를 설정합니다.
          console.error(error);
        });

      },


      nearBySearch(){
        // event.preventDefault();
        this.clearMarkers();

        this.places = [];
        const keyword = encodeURIComponent(this.title)
        const url = `https://maps.googleapis.com/maps/api/place/nearbysearch/json?keyword=${keyword}&location=${this.searchX},${this.searchY}&radius=${this.radius *
        1000}&key=AIzaSyC5Wsfp6CnCn4wltag9i5XrDNGvOwipkiY`
        axios.get(url).then(reponse => {
            
            var temp = reponse.data.results;
            // this.places = reponse.data.results;
            var idx = 0;
            temp.forEach((place) => {
              axios.get(`http://localhost/plan/api/photo/${place.place_id}`)
              .then(response =>{
                const photos = response.data;
                const url = 'https://maps.googleapis.com/maps/api/place/photo?maxwidth=40&photoreference=' + photos.photo + '&key=AIzaSyC5Wsfp6CnCn4wltag9i5XrDNGvOwipkiY'
                place.photo = url;
                // this.places.push(place);
                axios.get(`https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyC5Wsfp6CnCn4wltag9i5XrDNGvOwipkiY&place_id=` + place.place_id)
                .then(res => {
                  var t = res.data.result;
                  console.log(t.url);
                  place.url = t.url;
                  this.places.push(place);

                            // 마커 추가
                  const marker = new google.maps.Marker({
                    position: new google.maps.LatLng(place.geometry.location.lat, place.geometry.location.lng),
                    map: this.map,
                    icon: "https://maps.google.com/mapfiles/ms/icons/blue-dot.png"
                  });

                  marker.addListener('click', () => {
                    const infowindow = new google.maps.InfoWindow({
                      content: `<div><strong>${place.name}</strong><br>${place.vicinity}<br><a href="${place.url}" target="_blank">링크</a></div>`
                    });
                    infowindow.open(this.map, marker);
                  });

                  this.markers.push(marker);
                })
              })
            }
            )

        })
      },
      clearMarkers() {
      for (let i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
      },

      makePlan(place){
        this.plans.push(place);
      },

      deletePlan(plan, event) {
        event.preventDefault();
        event.stopPropagation();
        const index = this.plans.indexOf(plan);
        if (index > -1) {
          this.plans.splice(index, 1);
        }
      },

      submitForm(){
          const formData = {
          start_date: this.start_date,
          end_date: this.end_date,
          title: this.plan_title,
          plans: this.plans,
          content: this.content,
          plan_id: this.plan_id,
          store: "YES"
          // 기타 다른 필요한 데이터
        };

        axios.put("http://localhost/plan/api", formData)
        .then(response => {
          console.log(response.data);
        })
      },
      sendFormData(){
        
        if (this.plan_id){
          const formData = {
          start_date: this.start_date,
          end_date: this.end_date,
          title: this.plan_title,
          plans: this.plans,
          content: this.content,
          plan_id: this.plan_id,
          store: "NO",
          // 기타 다른 필요한 데이터
        };

        axios.put("http://localhost/plan/api", formData)
        .then(response => {
          console.log(response.data);
        })
        }else{
          const formData = {
          start_date: this.start_date,
          end_date: this.end_date,
          title: this.plan_title,
          plans: this.plans,
          content: this.content,
          store: "NO",
          // 기타 다른 필요한 데이터
        };
        axios.post("http://localhost/plan/api", formData)
        .then(response => {
          console.log(response.data);
          var id = response.data;
          this.plan_id = id.plan_id;
        })
        }
      },

      checkPlace(e){
        e.preventDefault();
        this.identifier = "place";
      },
      checkNearBy(e){
        e.preventDefault();
        this.identifier = "nearby";
      },
      checkRoad(e) {
        e.preventDefault();
          this.identifier = "road";
          this.initRoad().then(() => {
              // 초기화 작업 완료 후 실행할 코드
          });
        },
      checkRoute(e){
        e.preventDefault();
        this.identifier = "route";
        this.initRoute().then(() => {
              // 초기화 작업 완료 후 실행할 코드
          });
      },
      checkAir(e){
        e.preventDefault();
        this.identifier = "air";
      },
      checkHotel(e){
        e.preventDefault();
        this.identifier = "hotel";
        this.findHotel();
      },


      initRoad() {
  return new Promise((resolve) => {
    this.$nextTick(() => {
      let startAutocomplete = new google.maps.places.Autocomplete(this.$refs["start"], {
        bounds: new google.maps.LatLngBounds(),
      });

      startAutocomplete.addListener("place_changed", () => {
        let place = startAutocomplete.getPlace();
        console.log(place.formatted_address)
        this.findA = place.formatted_address;
        this.start = place.place_id;
      });

      let endAutocomplete = new google.maps.places.Autocomplete(this.$refs["end"], {
        bounds: new google.maps.LatLngBounds(),
      });

      endAutocomplete.addListener("place_changed", () => {
        let place = endAutocomplete.getPlace();
        console.log(place.formatted_address)
        this.findB = place.formatted_address;
        this.end = place.place_id;
      });

      resolve();
    });
  });
},
  initRoute() {
  return new Promise((resolve) => {
    this.$nextTick(() => {
      let startAutocomplete = new google.maps.places.Autocomplete(this.$refs["origin"], {
        bounds: new google.maps.LatLngBounds(),
      });

      startAutocomplete.addListener("place_changed", () => {
        let place = startAutocomplete.getPlace();
        console.log(place.formatted_address)
        this.origin.push(place);
      });

      let endAutocomplete = new google.maps.places.Autocomplete(this.$refs["destination"], {
        bounds: new google.maps.LatLngBounds(),
      });

      endAutocomplete.addListener("place_changed", () => {
        let place = endAutocomplete.getPlace();
        console.log(place.formatted_address)
        this.destination.push(place);
      });

      resolve();
    });
  });
},






      
    },

}


</script>

<style>

.flex-container {
  display: flex;
  flex-direction: column;
}

.flex-row {
  display: flex;
}
.custom-button {
  flex: 1;
  background-color: rgb(118, 232, 255);
  color: rgb(124, 122, 122);
  padding: 10px;
  margin: 5px;
  border: solid;
  border-radius: 5px;
}
</style>
