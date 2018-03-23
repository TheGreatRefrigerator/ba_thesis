/*
 *  Licensed to GIScience Research Group, Heidelberg University (GIScience)
 *
 *   http://www.giscience.uni-hd.de
 *   http://www.heigit.org
 *
 *  under one or more contributor license agreements. See the NOTICE file 
 *  distributed with this work for additional information regarding copyright 
 *  ownership. The GIScience licenses this file to you under the Apache License, 
 *  Version 2.0 (the "License"); you may not use this file except in compliance 
 *  with the License. You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package heigit.ors.routing.graphhopper.extensions.weighting;

import com.graphhopper.routing.util.FlagEncoder;
import com.graphhopper.routing.weighting.FastestWeighting;
import com.graphhopper.storage.GraphHopperStorage;
import com.graphhopper.storage.GraphStorage;
import com.graphhopper.util.AngleCalc;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.PMap;
import com.graphhopper.util.PointList;

public class AccelerationWeighting extends FastestWeighting {
	private GraphHopperStorage _ghStorage;
	private AngleCalc _angleCalc = new AngleCalc();
	private long _maxEdges;

	public AccelerationWeighting(FlagEncoder encoder, PMap map, GraphStorage graphStorage) {
		super(encoder, map);
		_ghStorage = (GraphHopperStorage)graphStorage;
		_maxEdges= _ghStorage.getEdges();
	}
	
	// extract turn angle from current and previous Edge geometry
	private double getTurnAngle(PointList currEdgeGeom, PointList prevEdgeGeom)
	{
		if (currEdgeGeom.size() >= 1 && prevEdgeGeom.size() >= 1)
		{
			int locIndex = prevEdgeGeom.size() - 1;
			double lon0 = prevEdgeGeom.getLon(locIndex - 1);
			double lat0 = prevEdgeGeom.getLat(locIndex - 1);
			double lon1 = prevEdgeGeom.getLon(locIndex);
			double lat1 = prevEdgeGeom.getLat(locIndex);

			double bearingBefore = Math.round(_angleCalc.calcAzimuth(lat0, lon0, lat1, lon1));

			double lon2 = currEdgeGeom.getLon(1);
			double lat2 = currEdgeGeom.getLat(1);

			double bearingAfter = (int)Math.round(_angleCalc.calcAzimuth(lat1, lon1, lat2, lon2));
			//bearingAfter =  _angleCalc.alignOrientation(bearingBefore, bearingAfter);
			double res = Math.abs(bearingBefore - bearingAfter);
			if (res > 180)
			{
				res = 360 - res;
				return res;
			}
			
			return res;
		}

		return 0.0;	
	}

	@Override
	public double calcWeight(EdgeIteratorState edgeState, boolean reverse, int prevOrNextEdgeId) {
		if (prevOrNextEdgeId == -1 || edgeState.getEdge() >= _maxEdges || prevOrNextEdgeId >= _maxEdges)
		{
			//TODO factor for first and last edge
			return 1.0;
		}

		PointList currEdgeGeom, prevEdgeGeom;
		if (reverse)
		{
			prevEdgeGeom =  _ghStorage.getEdgeIteratorState(edgeState.getEdge(), edgeState.getBaseNode()).fetchWayGeometry(3);
			currEdgeGeom =  _ghStorage.getEdgeIteratorState(prevOrNextEdgeId, edgeState.getBaseNode()).detach(true).fetchWayGeometry(3);
		}
		else
		{
			currEdgeGeom =  _ghStorage.getEdgeIteratorState(edgeState.getEdge(), edgeState.getAdjNode()).fetchWayGeometry(3);
			prevEdgeGeom =  _ghStorage.getEdgeIteratorState(prevOrNextEdgeId, edgeState.getBaseNode()).fetchWayGeometry(3);
		}
 
		double turnAngle = getTurnAngle(currEdgeGeom, prevEdgeGeom);
		
		if (isFullTurn(turnAngle))
		{
			// TODO factor for turn between last and current edge (applied to current edge)
			return 1.1;
		}

		return 1.0;
	}
	
	private boolean isFullTurn(double angle)
	{
		return angle > 50 && angle <= 140;
	}

	@Override
	public long calcMillis(EdgeIteratorState edgeState, boolean reverse, int prevOrNextEdgeId) {
		if (prevOrNextEdgeId == -1 || edgeState.getEdge() >= _maxEdges || prevOrNextEdgeId >= _maxEdges)
		{
			// compute acceleration for departure and finish edges.
			return 0;
		}

		PointList currEdgeGeom, prevEdgeGeom;
		if (reverse)
		{
			prevEdgeGeom =  _ghStorage.getEdgeIteratorState(edgeState.getEdge(), edgeState.getBaseNode()).fetchWayGeometry(3);
			currEdgeGeom =  _ghStorage.getEdgeIteratorState(prevOrNextEdgeId, edgeState.getBaseNode()).detach(true).fetchWayGeometry(3);
		}
		else
		{
			currEdgeGeom =  _ghStorage.getEdgeIteratorState(edgeState.getEdge(), edgeState.getAdjNode()).fetchWayGeometry(3);
			prevEdgeGeom =  _ghStorage.getEdgeIteratorState(prevOrNextEdgeId, edgeState.getBaseNode()).fetchWayGeometry(3);
		}

		double turnAngle = getTurnAngle(currEdgeGeom, prevEdgeGeom);
		
		if (isFullTurn(turnAngle))
		{
			/*double speed = 1000*edgeState.getDistance()/weight * SPEED_CONV; 
			double distAfter = currEdgeGeom.calcDistance(Helper.DIST_EARTH);

			// compute acceleration influence only for a segment after the turn.
			int totalSeconds = (int)(weight/1000) + 100;
            int accelTime = 0;
			double accelDist = 0.0;
            
			for (int i= 0; i < totalSeconds; ++i)
			{
				double currSpeed = (i + 1)* 2.5*0.3048;

				accelTime = i + 1;
				accelDist += currSpeed;
		
				if (currSpeed >= speed/SPEED_CONV)
					break;
				if (accelDist > distAfter)
					break;
			}
			
			accelTime *= 1000;
			long fullSpeedTime = 0;
			if (accelDist < distAfter)
			{
				fullSpeedTime = (long)((distAfter - accelDist)/speed * SPEED_CONV);
			}
					
			return (long)(-weight + accelTime + fullSpeedTime);*/
			
			return 0;// 10 seconds for every turn
		}

		return 0;
	}
}