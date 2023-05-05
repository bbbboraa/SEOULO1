package com.example.seoulo1;

import static android.opengl.Matrix.length;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class LocalSelectActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_select);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.localmap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);
        mMap = googleMap;
        double addr[] = {37.56100278, 126.9996417, 37.57037778, 126.9816417, 37.5360944, 126.9675222, 37.57636667, 126.9388972
                        , 37.571625, 127.0421417, 37.58638333, 127.0203333, 37.56061111, 127.039, 37.56070556, 126.9105306 };
                                  //중구                    //종로구                   //용산구                //서대문구
                                  //동대문구                 //성북구                   //성동구                //마포구

        for (int i = 0; i< addr.length; i=+2){
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions
                    .position(new LatLng(addr[i], addr[i+1]))
                    .title("마커"+(i/2));
            mMap.addMarker(markerOptions);
        }

        Polygon poly1 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)  //중구 //JungGu
                .add(
                        new LatLng(37.544062989758594, 127.00854659142894),
                        new LatLng(37.54385947805103, 127.00727818360471),
                        new LatLng(37.546169758665414, 127.00499711608721),
                        new LatLng(37.54820235864802, 127.0061334023129),
                        new LatLng(37.550159406110104, 127.00436818301327),
                        new LatLng(37.549694559809545, 126.99832516302801),
                        new LatLng(37.54722934282707, 126.995229135048),
                        new LatLng(37.55368689494708, 126.98541456064552),
                        new LatLng(37.55320655210504, 126.97874667968763),
                        new LatLng(37.55522076659584, 126.97654602427454),
                        new LatLng(37.55308718044556, 126.97642899633566),
                        new LatLng(37.55487749311664, 126.97240854546743),
                        new LatLng(37.5548766923893, 126.9691718124876),
                        new LatLng(37.55566236579088, 126.9691850696746),
                        new LatLng(37.55155543391863, 126.96233786542686),
                        new LatLng(37.55498984534305, 126.96173858545431),
                        new LatLng(37.55695455613004, 126.96343068837372),
                        new LatLng(37.5590262922649, 126.9616731414449),
                        new LatLng(37.56197662569172, 126.96946316364357),
                        new LatLng(37.56582132960869, 126.96669525397355),
                        new LatLng(37.56824746386509, 126.96909838710842),
                        new LatLng(37.569485309984174, 126.97637402412326),
                        new LatLng(37.56810323716611, 126.98905202099309),
                        new LatLng(37.56961739576364, 127.00225936812329),
                        new LatLng(37.56966688588187, 127.0152677241078),
                        new LatLng(37.572022763755164, 127.0223363152772),
                        new LatLng(37.57190723475508, 127.02337770475695),
                        new LatLng(37.56973041414113, 127.0234585247501),
                        new LatLng(37.565200182350495, 127.02358387477513),
                        new LatLng(37.56505173515675, 127.02678930885806),
                        new LatLng(37.563390358462826, 127.02652159646888),
                        new LatLng(37.5607276739534, 127.02339232029838),
                        new LatLng(37.55779412537163, 127.0228934248264),
                        new LatLng(37.556850715898484, 127.01807638779917),
                        new LatLng(37.55264513061776, 127.01620129137214),
                        new LatLng(37.547466935106435, 127.00931996404753),
                        new LatLng(37.54502351209897, 127.00815187343248),
                        new LatLng(37.544062989758594, 127.00854659142894)
                )
        );
        poly1.setTag("JungGu");
        poly1.setStrokeColor(android.R.color.holo_green_light);
        poly1.setClickable(true);

        Polygon poly2 =  googleMap.addPolygon(new PolygonOptions()
                .clickable(true)  //종로구 //JongnoGu
                .add(
                        new LatLng(37.631840777111364, 126.9749313865046),
                        new LatLng(37.632194205253654, 126.97609588529602),
                        new LatLng(37.629026103322374, 126.97496405167149),
                        new LatLng(37.6285585388996, 126.97992605309885),
                        new LatLng(37.626378096236195, 126.97960492198952),
                        new LatLng(37.6211493968146, 126.98365245774505),
                        new LatLng(37.6177725051378, 126.9837302191854),
                        new LatLng(37.613985109550605, 126.98658977758268),
                        new LatLng(37.611364924201304, 126.98565700183143),
                        new LatLng(37.60401657024552, 126.98665951539246),
                        new LatLng(37.60099164566844, 126.97852019816328),
                        new LatLng(37.59790270809407, 126.97672287261275),
                        new LatLng(37.59447673441787, 126.98544283754865),
                        new LatLng(37.59126960661375, 126.98919808879788),
                        new LatLng(37.592300831997434, 127.0009511248032),
                        new LatLng(37.58922302426079, 127.00228260552726),
                        new LatLng(37.586091007146834, 127.00667090686603),
                        new LatLng(37.58235007703611, 127.00677925856456),
                        new LatLng(37.58047228501006, 127.00863575242668),
                        new LatLng(37.58025588757531, 127.01058748333907),
                        new LatLng(37.582338528091164, 127.01483104096094),
                        new LatLng(37.581693162424465, 127.01673289259993),
                        new LatLng(37.57758802896556, 127.01812215416163),
                        new LatLng(37.5788668917658, 127.02140099081309),
                        new LatLng(37.578034045208426, 127.02313962015988),
                        new LatLng(37.57190723475508, 127.02337770475695),
                        new LatLng(37.56966688588187, 127.0152677241078),
                        new LatLng(37.56961739576364, 127.00225936812329),
                        new LatLng(37.5681357695346, 126.99014772014593),
                        new LatLng(37.569315246023024, 126.9732046364419),
                        new LatLng(37.56824746386509, 126.96909838710842),
                        new LatLng(37.56582132960869, 126.96669525397355),
                        new LatLng(37.57874076105342, 126.95354824618335),
                        new LatLng(37.581020184166476, 126.95812059678624),
                        new LatLng(37.59354736740056, 126.95750665936443),
                        new LatLng(37.595061575856455, 126.9590412421402),
                        new LatLng(37.59833350100327, 126.9576941779143),
                        new LatLng(37.59875701675023, 126.95306020161668),
                        new LatLng(37.602476031211225, 126.95237386792348),
                        new LatLng(37.60507154496655, 126.95404376087069),
                        new LatLng(37.60912809443569, 126.95032198271032),
                        new LatLng(37.615539700280216, 126.95072546923387),
                        new LatLng(37.62433621196653, 126.94900237336302),
                        new LatLng(37.62642708817027, 126.95037844036497),
                        new LatLng(37.629590994617104, 126.95881385457929),
                        new LatLng(37.629794440379136, 126.96376660599225),
                        new LatLng(37.632373740990175, 126.97302793692637),
                        new LatLng(37.631840777111364, 126.9749313865046)
                )
        );
        poly2.setTag("JongnoGu");
        poly2.setStrokeColor(android.R.color.holo_red_light);
        poly2.setClickable(true);

        Polygon poly3 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)  //용산구 //YongsanGu
                .add(
                        new LatLng(37.5548768201904, 126.96966524449994),
                        new LatLng(37.55308718044556, 126.97642899633566),
                        new LatLng(37.55522076659584, 126.97654602427454),
                        new LatLng(37.55320655210504, 126.97874667968763),
                        new LatLng(37.55368689494708, 126.98541456064552),
                        new LatLng(37.54722934282707, 126.995229135048),
                        new LatLng(37.549694559809545, 126.99832516302801),
                        new LatLng(37.550159406110104, 127.00436818301327),
                        new LatLng(37.54820235864802, 127.0061334023129),
                        new LatLng(37.546169758665414, 127.00499711608721),
                        new LatLng(37.54385947805103, 127.00727818360471),
                        new LatLng(37.54413326436179, 127.00898460651953),
                        new LatLng(37.539639030116945, 127.00959054834321),
                        new LatLng(37.537681185520256, 127.01726163044557),
                        new LatLng(37.53378887274516, 127.01719284893274),
                        new LatLng(37.52290225898471, 127.00614038053493),
                        new LatLng(37.51309192794448, 126.99070240960813),
                        new LatLng(37.50654651085339, 126.98553683648308),
                        new LatLng(37.50702053393398, 126.97524914998174),
                        new LatLng(37.51751820477105, 126.94988506562748),
                        new LatLng(37.52702918583156, 126.94987870367682),
                        new LatLng(37.534519656862926, 126.94481851935942),
                        new LatLng(37.537500243531994, 126.95335659960566),
                        new LatLng(37.54231338779177, 126.95817394011969),
                        new LatLng(37.54546318600178, 126.95790512689311),
                        new LatLng(37.548791603525764, 126.96371984820232),
                        new LatLng(37.55155543391863, 126.96233786542686),
                        new LatLng(37.5541513366375, 126.9657135934734),
                        new LatLng(37.55566236579088, 126.9691850696746),
                        new LatLng(37.5548768201904, 126.96966524449994)
                )
        );
        poly3.setTag("YongsanGu");
        poly3.setStrokeColor(android.R.color.holo_blue_light);
        poly3.setClickable(true);

        Polygon poly4 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)  //서대문구 //SeodaemunGu
                .add(
                        new LatLng(37.59851932019209, 126.95347706883003),
                        new LatLng(37.5992407011344, 126.95499403097206),
                        new LatLng(37.59833350100327, 126.9576941779143),
                        new LatLng(37.595061575856455, 126.9590412421402),
                        new LatLng(37.59354736740056, 126.95750665936443),
                        new LatLng(37.581020184166476, 126.95812059678624),
                        new LatLng(37.57874076105342, 126.95354824618335),
                        new LatLng(37.56197662569172, 126.96946316364357),
                        new LatLng(37.5575156365052, 126.95991288916548),
                        new LatLng(37.55654562007193, 126.9413708153468),
                        new LatLng(37.555098093384, 126.93685861757348),
                        new LatLng(37.55884751347576, 126.92659242918415),
                        new LatLng(37.5633319104926, 126.92828128083327),
                        new LatLng(37.56510367293256, 126.92601582346325),
                        new LatLng(37.57082994377989, 126.9098094620638),
                        new LatLng(37.57599550420081, 126.902091747923),
                        new LatLng(37.587223103650295, 126.91284666446226),
                        new LatLng(37.58541570520177, 126.91531241017965),
                        new LatLng(37.585870567159255, 126.91638068573187),
                        new LatLng(37.583095195853055, 126.9159399866114),
                        new LatLng(37.583459593417196, 126.92175886498167),
                        new LatLng(37.587104600730505, 126.92388813813815),
                        new LatLng(37.588386594820484, 126.92800815682232),
                        new LatLng(37.59157595859555, 126.92776504133688),
                        new LatLng(37.59455434247408, 126.93027139545339),
                        new LatLng(37.59869748704149, 126.94088480070904),
                        new LatLng(37.60065830191363, 126.9414041615336),
                        new LatLng(37.60305781086164, 126.93995273804141),
                        new LatLng(37.610598531321436, 126.95037536795142),
                        new LatLng(37.6083605525023, 126.95056259057313),
                        new LatLng(37.60507154496655, 126.95404376087069),
                        new LatLng(37.602476031211225, 126.95237386792348),
                        new LatLng(37.59851932019209, 126.95347706883003)
                )
        );
        poly4.setTag("SeodaemunGu");
        poly4.setStrokeColor(android.R.color.holo_orange_light);
        poly4.setClickable(true);

        Polygon poly5 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)  //동대문구 //DongdaemunGu
                .add(
                        new LatLng(37.607062869017085, 127.07111288773496),
                        new LatLng(37.60107201319839, 127.07287376670605),
                        new LatLng(37.59724304056685, 127.06949105186925),
                        new LatLng(37.58953367466315, 127.07030363208528),
                        new LatLng(37.58651213184981, 127.07264218709383),
                        new LatLng(37.5849555116177, 127.07216063016078),
                        new LatLng(37.58026781100598, 127.07619547037923),
                        new LatLng(37.571869232268774, 127.0782018408153),
                        new LatLng(37.559961773835425, 127.07239004251258),
                        new LatLng(37.56231553903832, 127.05876047165025),
                        new LatLng(37.57038253579033, 127.04794980454399),
                        new LatLng(37.572878529071055, 127.04263554582458),
                        new LatLng(37.57302061077518, 127.0381755492195),
                        new LatLng(37.56978273516453, 127.03099733100001),
                        new LatLng(37.57190723475508, 127.02337770475695),
                        new LatLng(37.57838361223621, 127.0232348231103),
                        new LatLng(37.58268174514337, 127.02953994610249),
                        new LatLng(37.58894739851823, 127.03553876830637),
                        new LatLng(37.5911852565689, 127.03621919708065),
                        new LatLng(37.59126734230753, 127.03875553445558),
                        new LatLng(37.5956815721534, 127.04062845365279),
                        new LatLng(37.5969637344377, 127.04302522879048),
                        new LatLng(37.59617641777492, 127.04734129391157),
                        new LatLng(37.60117358544485, 127.05101351973708),
                        new LatLng(37.600149587503246, 127.05209540476308),
                        new LatLng(37.60132672748398, 127.05508130598699),
                        new LatLng(37.6010580545608, 127.05917142337097),
                        new LatLng(37.605121767227374, 127.06219611364686),
                        new LatLng(37.607062869017085, 127.07111288773496)
                )
        );
        poly5.setTag("DongdaemunGu");
        poly5.setStrokeColor(android.R.color.holo_purple);
        poly5.setClickable(true);

        Polygon poly6 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)  //성북구 //SeongbukGu
                .add(
                        new LatLng(37.631446839436855, 126.99372381657889),
                        new LatLng(37.626192451322005, 126.99927047335905),
                        new LatLng(37.62382095469671, 127.00488450145781),
                        new LatLng(37.624026217174986, 127.00788862747375),
                        new LatLng(37.6205124078061, 127.00724034082933),
                        new LatLng(37.61679651952433, 127.01014412786792),
                        new LatLng(37.61472018601129, 127.01451127202589),
                        new LatLng(37.614629670135216, 127.01757841621624),
                        new LatLng(37.61137091590441, 127.02219857751122),
                        new LatLng(37.612692696824915, 127.02642583551054),
                        new LatLng(37.612367438936786, 127.03018593770908),
                        new LatLng(37.60896889076571, 127.0302525167858),
                        new LatLng(37.61279787695882, 127.03730791358603),
                        new LatLng(37.62426467261789, 127.04973339977498),
                        new LatLng(37.61449950127667, 127.06174181124696),
                        new LatLng(37.61561580859776, 127.06985247014711),
                        new LatLng(37.61351359068103, 127.07170798866412),
                        new LatLng(37.60762512162974, 127.07105453180604),
                        new LatLng(37.605121767227374, 127.06219611364686),
                        new LatLng(37.6010580545608, 127.05917142337097),
                        new LatLng(37.60132672748398, 127.05508130598699),
                        new LatLng(37.600149587503246, 127.05209540476308),
                        new LatLng(37.60117358544485, 127.05101351973708),
                        new LatLng(37.59617641777492, 127.04734129391157),
                        new LatLng(37.59644879095525, 127.04184728392097),
                        new LatLng(37.59126734230753, 127.03875553445558),
                        new LatLng(37.5911852565689, 127.03621919708065),
                        new LatLng(37.58894739851823, 127.03553876830637),
                        new LatLng(37.58268174514337, 127.02953994610249),
                        new LatLng(37.57782865303167, 127.02296295333255),
                        new LatLng(37.57889204835333, 127.02179043639809),
                        new LatLng(37.57758802896556, 127.01812215416163),
                        new LatLng(37.581693162424465, 127.01673289259993),
                        new LatLng(37.582338528091164, 127.01483104096094),
                        new LatLng(37.58025588757531, 127.01058748333907),
                        new LatLng(37.58047228501006, 127.00863575242668),
                        new LatLng(37.58235007703611, 127.00677925856456),
                        new LatLng(37.586091007146834, 127.00667090686603),
                        new LatLng(37.58922302426079, 127.00228260552726),
                        new LatLng(37.592300831997434, 127.0009511248032),
                        new LatLng(37.59126960661375, 126.98919808879788),
                        new LatLng(37.59447673441787, 126.98544283754865),
                        new LatLng(37.59790270809407, 126.97672287261275),
                        new LatLng(37.60099164566844, 126.97852019816328),
                        new LatLng(37.60451393107786, 126.98678626394351),
                        new LatLng(37.611364924201304, 126.98565700183143),
                        new LatLng(37.613985109550605, 126.98658977758268),
                        new LatLng(37.6177725051378, 126.9837302191854),
                        new LatLng(37.6211493968146, 126.98365245774505),
                        new LatLng(37.626378096236195, 126.97960492198952),
                        new LatLng(37.6285585388996, 126.97992605309885),
                        new LatLng(37.62980449548538, 126.97468284124939),
                        new LatLng(37.633657663246694, 126.97740053878216),
                        new LatLng(37.63476479485093, 126.98154674721893),
                        new LatLng(37.63780700422825, 126.9849494717052),
                        new LatLng(37.63654916557213, 126.98446028560235)
                )
        );
        poly6.setTag("SeongbukGu");
        poly6.setStrokeColor(android.R.color.darker_gray);
        poly6.setClickable(true);

        Polygon poly7 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)  //성동구 //SeondongGu
                .add(
                        new LatLng(37.57275246810175, 127.04241813085706),
                        new LatLng(37.57038253579033, 127.04794980454399),
                        new LatLng(37.56231553903832, 127.05876047165025),
                        new LatLng(37.5594131360664, 127.07373408220053),
                        new LatLng(37.52832388381049, 127.05621773388143),
                        new LatLng(37.53423885672233, 127.04604398310076),
                        new LatLng(37.53582328355087, 127.03979942567628),
                        new LatLng(37.53581367627865, 127.0211714455564),
                        new LatLng(37.53378887274516, 127.01719284893274),
                        new LatLng(37.537681185520256, 127.01726163044557),
                        new LatLng(37.53938672166098, 127.00993448922989),
                        new LatLng(37.54157804358092, 127.00879872996808),
                        new LatLng(37.54502351209897, 127.00815187343248),
                        new LatLng(37.547466935106435, 127.00931996404753),
                        new LatLng(37.55264513061776, 127.01620129137214),
                        new LatLng(37.556850715898484, 127.01807638779917),
                        new LatLng(37.55779412537163, 127.0228934248264),
                        new LatLng(37.5607276739534, 127.02339232029838),
                        new LatLng(37.563390358462826, 127.02652159646888),
                        new LatLng(37.56505173515675, 127.02678930885806),
                        new LatLng(37.565200182350495, 127.02358387477513),
                        new LatLng(37.57190723475508, 127.02337770475695),
                        new LatLng(37.56978273516453, 127.03099733100001),
                        new LatLng(37.57302061077518, 127.0381755492195),
                        new LatLng(37.57275246810175, 127.04241813085706)
                )
        );
        poly7.setTag("SeondongGu");
        poly7.setStrokeColor(android.R.color.black);
        poly7.setClickable(true);

        Polygon poly8 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)  //마포구 //MapoGu
                .add(
                        new LatLng(37.584651324803644, 126.88883849288884),
                        new LatLng(37.57082994377989, 126.9098094620638),
                        new LatLng(37.56510367293256, 126.92601582346325),
                        new LatLng(37.5633319104926, 126.92828128083327),
                        new LatLng(37.55884751347576, 126.92659242918415),
                        new LatLng(37.55675317809392, 126.93190919632814),
                        new LatLng(37.555098093384, 126.93685861757348),
                        new LatLng(37.55654562007193, 126.9413708153468),
                        new LatLng(37.557241466445234, 126.95913438471307),
                        new LatLng(37.55908394430372, 126.96163689468189),
                        new LatLng(37.55820141918588, 126.96305432966605),
                        new LatLng(37.554784413504734, 126.9617251098019),
                        new LatLng(37.548791603525764, 126.96371984820232),
                        new LatLng(37.54546318600178, 126.95790512689311),
                        new LatLng(37.54231338779177, 126.95817394011969),
                        new LatLng(37.539468942052544, 126.955731506394),
                        new LatLng(37.536292068277106, 126.95128907260018),
                        new LatLng(37.53569162926515, 126.94627494388307),
                        new LatLng(37.53377712226906, 126.94458373402794),
                        new LatLng(37.54135238063506, 126.93031191951576),
                        new LatLng(37.539036674424615, 126.9271006565075),
                        new LatLng(37.54143034750605, 126.9224138272872),
                        new LatLng(37.54141748538761, 126.90483000187002),
                        new LatLng(37.548015078285694, 126.89890097452322),
                        new LatLng(37.56300401736817, 126.86623824787709),
                        new LatLng(37.57178997971358, 126.85363039091744),
                        new LatLng(37.57379738998644, 126.85362646212587),
                        new LatLng(37.57747251471329, 126.864939928088),
                        new LatLng(37.5781913017327, 126.87625939970273),
                        new LatLng(37.57977132158497, 126.87767870371688),
                        new LatLng(37.584440882833654, 126.87653889183002),
                        new LatLng(37.59079311146897, 126.88205386700724),
                        new LatLng(37.584651324803644, 126.88883849288884)
                )
        );
        poly8.setTag("MapoGu");
        poly8.setStrokeColor(android.R.color.holo_blue_dark);
        //poly8.setClickable(true);
    }

    @Override
    public void onPolylineClick(@NonNull Polyline polyline) { }
    @Override
    public void onPolygonClick(@NonNull Polygon polygon) { }
}