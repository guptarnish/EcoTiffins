package es.hol.ecotiffins.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class AreaPolygons {
    public final static int VAISHALI = 1;
    public final static int CHITRAKOOT = 2;
    public final static int SODALA = 3;
    public final static int DCM = 4;
    public final static int SINDHI_CAMP = 5;
    public final static int GOPALPURA = 6;

    public AreaPolygons() {

    }

    public ArrayList<LatLng> getAreaLatLngs(int area) {
        switch (area) {
            case AreaPolygons.VAISHALI:
                return getVaishali();
            case AreaPolygons.CHITRAKOOT:
                return getChitrakoot();
            case AreaPolygons.SODALA:
                return getSodala();
            case AreaPolygons.DCM:
                return getDCM();
            case AreaPolygons.SINDHI_CAMP:
                return getSindhiCamp();
            case AreaPolygons.GOPALPURA:
                return getGopalpura();
            default:
                return new ArrayList<>();
        }
    }

    private ArrayList<LatLng> getVaishali() {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(26.920935, 75.730367));
        latLngs.add(new LatLng(26.920055, 75.740152));
        latLngs.add(new LatLng(26.922351, 75.743371));
        latLngs.add(new LatLng(26.915042, 75.743628));
        latLngs.add(new LatLng(26.915348, 75.745988));
        latLngs.add(new LatLng(26.911713, 75.746074));
        latLngs.add(new LatLng(26.911445, 75.753026));
        latLngs.add(new LatLng(26.905972, 75.752812));
        latLngs.add(new LatLng(26.905657, 75.750709));
        latLngs.add(new LatLng(26.902327, 75.750602));
        latLngs.add(new LatLng(26.901763, 75.747566));
        latLngs.add(new LatLng(26.905197, 75.747383));
        latLngs.add(new LatLng(26.905159, 75.745087));
        latLngs.add(new LatLng(26.905848, 75.740345));
        latLngs.add(new LatLng(26.901887, 75.740581));
        latLngs.add(new LatLng(26.901868, 75.743499));
        latLngs.add(new LatLng(26.898270, 75.743907));
        latLngs.add(new LatLng(26.895295, 75.733543));
        latLngs.add(new LatLng(26.894414, 75.733800));
        latLngs.add(new LatLng(26.893649, 75.731526));
        latLngs.add(new LatLng(26.892194, 75.730024));
        latLngs.add(new LatLng(26.892654, 75.729595));
        latLngs.add(new LatLng(26.892041, 75.728822));
        latLngs.add(new LatLng(26.892032, 75.727513));
        latLngs.add(new LatLng(26.891247, 75.725174));
        latLngs.add(new LatLng(26.890941, 75.725132));
        latLngs.add(new LatLng(26.890903, 75.723994));
        latLngs.add(new LatLng(26.892089, 75.723994));
        latLngs.add(new LatLng(26.892089, 75.724445));
        latLngs.add(new LatLng(26.894424, 75.724316));
        latLngs.add(new LatLng(26.897677, 75.724874));
        latLngs.add(new LatLng(26.897849, 75.721891));
        latLngs.add(new LatLng(26.898155, 75.721956));
        latLngs.add(new LatLng(26.898921, 75.722857));
        latLngs.add(new LatLng(26.898634, 75.725153));
        latLngs.add(new LatLng(26.898366, 75.728887));
        latLngs.add(new LatLng(26.898672, 75.731719));
        latLngs.add(new LatLng(26.903513, 75.728586));
        latLngs.add(new LatLng(26.909675, 75.729144));
        latLngs.add(new LatLng(26.909751, 75.726719));
        latLngs.add(new LatLng(26.915396, 75.727084));
        latLngs.add(new LatLng(26.915224, 75.729766));
        latLngs.add(new LatLng(26.920925, 75.730346));
        return latLngs;
    }

    private ArrayList<LatLng> getChitrakoot() {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(26.905966, 75.735626));
        latLngs.add(new LatLng(26.905793, 75.740347));
        latLngs.add(new LatLng(26.901909, 75.740626));
        latLngs.add(new LatLng(26.901832, 75.743373));
        latLngs.add(new LatLng(26.898273, 75.743887));
        latLngs.add(new LatLng(26.897527, 75.741463));
        latLngs.add(new LatLng(26.895843, 75.741828));
        latLngs.add(new LatLng(26.895613, 75.739145));
        latLngs.add(new LatLng(26.896819, 75.738974));
        latLngs.add(new LatLng(26.895307, 75.733459));
        latLngs.add(new LatLng(26.902847, 75.728867));
        latLngs.add(new LatLng(26.903631, 75.728588));
        latLngs.add(new LatLng(26.904626, 75.728567));
        latLngs.add(new LatLng(26.904741, 75.735712));
        latLngs.add(new LatLng(26.905966, 75.735626));
        return latLngs;
    }

    private ArrayList<LatLng> getDCM() {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(26.895899, 75.744889));
        latLngs.add(new LatLng(26.897143, 75.747270));
        latLngs.add(new LatLng(26.897009, 75.747957));
        latLngs.add(new LatLng(26.896627, 75.748193));
        latLngs.add(new LatLng(26.896684, 75.748472));
        latLngs.add(new LatLng(26.895038, 75.749395));
        latLngs.add(new LatLng(26.895823, 75.750983));
        latLngs.add(new LatLng(26.890924, 75.751262));
        latLngs.add(new LatLng(26.891058, 75.750167));
        latLngs.add(new LatLng(26.890101, 75.748558));
        latLngs.add(new LatLng(26.893335, 75.746112));
        latLngs.add(new LatLng(26.893447, 75.746380));
        latLngs.add(new LatLng(26.895887, 75.744889));
        return latLngs;
    }

    private ArrayList<LatLng> getSindhiCamp() {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(26.928449, 75.797681));
        latLngs.add(new LatLng(26.930400, 75.801736));
        latLngs.add(new LatLng(26.928009, 75.805019));
        latLngs.add(new LatLng(26.926842, 75.808581));
        latLngs.add(new LatLng(26.920930, 75.803324));
        latLngs.add(new LatLng(26.919437, 75.801908));
        latLngs.add(new LatLng(26.917883, 75.801296));
        latLngs.add(new LatLng(26.920188, 75.794226));
        latLngs.add(new LatLng(26.920676, 75.794044));
        latLngs.add(new LatLng(26.921241, 75.795857));
        latLngs.add(new LatLng(26.923125, 75.795728));
        latLngs.add(new LatLng(26.925010, 75.795964));
        latLngs.add(new LatLng(26.926445, 75.798893));
        latLngs.add(new LatLng(26.928449, 75.797691));
        return latLngs;
    }

    private ArrayList<LatLng> getGopalpura() {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(26.892612, 75.782104));
        latLngs.add(new LatLng(26.891655, 75.788456));
        latLngs.add(new LatLng(26.890086, 75.788713));
        latLngs.add(new LatLng(26.867233, 75.787297));
        latLngs.add(new LatLng(26.866200, 75.796481));
        latLngs.add(new LatLng(26.857203, 75.795151));
        latLngs.add(new LatLng(26.857930, 75.790172));
        latLngs.add(new LatLng(26.861577, 75.790602));
        latLngs.add(new LatLng(26.861826, 75.782770));
        latLngs.add(new LatLng(26.865367, 75.783542));
        latLngs.add(new LatLng(26.865826, 75.783220));
        latLngs.add(new LatLng(26.867032, 75.785087));
        latLngs.add(new LatLng(26.868564, 75.783864));
        latLngs.add(new LatLng(26.867626, 75.782512));
        latLngs.add(new LatLng(26.867607, 75.781589));
        latLngs.add(new LatLng(26.869865, 75.782469));
        latLngs.add(new LatLng(26.871320, 75.780538));
        latLngs.add(new LatLng(26.872928, 75.777255));
        latLngs.add(new LatLng(26.877100, 75.773242));
        latLngs.add(new LatLng(26.878708, 75.770474));
        latLngs.add(new LatLng(26.880316, 75.771182));
        latLngs.add(new LatLng(26.881177, 75.770217));
        latLngs.add(new LatLng(26.882134, 75.771139));
        latLngs.add(new LatLng(26.882459, 75.772491));
        latLngs.add(new LatLng(26.882498, 75.773285));
        latLngs.add(new LatLng(26.883971, 75.775002));
        latLngs.add(new LatLng(26.884928, 75.774659));
        latLngs.add(new LatLng(26.885713, 75.775152));
        latLngs.add(new LatLng(26.884220, 75.777341));
        latLngs.add(new LatLng(26.888526, 75.780903));
        latLngs.add(new LatLng(26.888564, 75.780023));
        latLngs.add(new LatLng(26.889502, 75.780023));
        latLngs.add(new LatLng(26.889521, 75.781203));
        latLngs.add(new LatLng(26.890153, 75.781761));
        latLngs.add(new LatLng(26.892392, 75.782040));
        return latLngs;
    }

    private ArrayList<LatLng> getSodala() {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(26.910112, 75.768706));
        latLngs.add(new LatLng(26.910112, 75.769156));
        latLngs.add(new LatLng(26.910246, 75.769145));
        latLngs.add(new LatLng(26.910217, 75.769682));
        latLngs.add(new LatLng(26.910466, 75.769725));
        latLngs.add(new LatLng(26.910772, 75.770433));
        latLngs.add(new LatLng(26.910839, 75.770798));
        latLngs.add(new LatLng(26.910973, 75.771195));
        latLngs.add(new LatLng(26.911078, 75.773276));
        latLngs.add(new LatLng(26.907959, 75.772107));
        latLngs.add(new LatLng(26.908533, 75.776205));
        latLngs.add(new LatLng(26.906036, 75.776935));
        latLngs.add(new LatLng(26.902917, 75.774800));
        latLngs.add(new LatLng(26.901721, 75.777492));
        latLngs.add(new LatLng(26.901099, 75.777246));
        latLngs.add(new LatLng(26.901415, 75.776130));
        latLngs.add(new LatLng(26.901195, 75.775733));
        latLngs.add(new LatLng(26.901606, 75.774692));
        latLngs.add(new LatLng(26.898879, 75.774682));
        latLngs.add(new LatLng(26.898889, 75.775025));
        latLngs.add(new LatLng(26.895732, 75.774907));
        latLngs.add(new LatLng(26.896344, 75.770701));
        latLngs.add(new LatLng(26.897722, 75.771055));
        latLngs.add(new LatLng(26.898669, 75.772203));
        latLngs.add(new LatLng(26.901807, 75.772278));
        latLngs.add(new LatLng(26.899138, 75.767161));
        latLngs.add(new LatLng(26.901300, 75.766699));
        latLngs.add(new LatLng(26.903204, 75.766774));
        latLngs.add(new LatLng(26.903281, 75.766077));
        latLngs.add(new LatLng(26.902649, 75.764146));
        latLngs.add(new LatLng(26.904132, 75.763384));
        latLngs.add(new LatLng(26.904534, 75.761678));
        latLngs.add(new LatLng(26.908438, 75.765594));
        latLngs.add(new LatLng(26.906371, 75.769403));
        latLngs.add(new LatLng(26.908017, 75.768652));
        latLngs.add(new LatLng(26.910121, 75.768706));
        return latLngs;
    }
}
